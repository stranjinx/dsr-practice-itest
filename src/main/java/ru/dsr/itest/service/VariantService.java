package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.ConfigId;
import ru.dsr.itest.db.entity.Variant;
import ru.dsr.itest.db.entity.VariantConfig;
import ru.dsr.itest.db.repository.QuestionRepository;
import ru.dsr.itest.db.repository.VariantConfigRepository;
import ru.dsr.itest.db.repository.VariantRepository;
import ru.dsr.itest.rest.dto.VariantDto;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class VariantService {
    private final VariantRepository variantRepository;
    private final VariantConfigRepository variantConfigRepository;
    private final QuestionRepository questionRepository;
    private final ActionValidator actionValidator;

    @Transactional
    public void deleteVariant(Integer creator, Integer id) {
        actionValidator.validateDeleteVariant(creator, id);
        variantRepository.deleteById(id);
    }

    @Transactional
    public void updateVariantSettings(Integer creator, VariantDto settings) {
        actionValidator.validateUpdateTest(creator, settings.getTestId());
        checkCollisionAndExisting(settings);

        Variant variant = settings.getId() == -1 ?
                new Variant() :
                variantRepository.findById(settings.getId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        variant.setTestId(settings.getTestId());
        variant.setNumber(settings.getNumber());
        variantRepository.save(variant);
        updateVariantQuestions(variant.getId(), settings.getQuestions());
    }

    private void checkCollisionAndExisting(VariantDto settings) {
        List<Integer> oldQuestions = settings.getQuestions()
                .values()
                .stream()
                .filter(a -> a != -1)
                .toList();
        Set<Integer> uniqueQuestions = new HashSet<>(oldQuestions.size());
        for (Integer id : oldQuestions) {
            if (!uniqueQuestions.add(id))
                throw new ResponseStatusException(BAD_REQUEST);
        }
        if (!questionRepository.existsAllByTestId(
                settings.getTestId(),
                uniqueQuestions,
                uniqueQuestions.size()))
            throw new ResponseStatusException(BAD_REQUEST);
    }

    private void updateVariantQuestions(Integer variant, Map<Integer, Integer> questionsByNumber) {
        List<VariantConfig> configs = new ArrayList<>();

        for (Map.Entry<Integer, Integer> e : questionsByNumber.entrySet()) {
            VariantConfig config =  new VariantConfig();
            config.setNumber(e.getKey());
            config.setConfigId(new ConfigId(variant, e.getValue()));
            configs.add(config);
        }

        variantConfigRepository.deleteAllByConfigId_Variant(variant);
        variantConfigRepository.saveAll(configs);
    }


    public List<Variant> findAll(Integer creator, Integer test) {
        actionValidator.validateGetTest(creator, test);
        return variantRepository.findAllByTestId(test);
    }

    public Variant findVariant(Integer creator, Integer id) {
        actionValidator.validateGetVariant(creator, id);
        return variantRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
