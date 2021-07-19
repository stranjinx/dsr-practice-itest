package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.*;
import ru.dsr.itest.db.repository.QuestionRepository;
import ru.dsr.itest.db.repository.TestRepository;
import ru.dsr.itest.db.repository.VariantConfigRepository;
import ru.dsr.itest.db.repository.VariantRepository;
import ru.dsr.itest.rest.dto.VariantDto;

import java.util.*;

import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class VariantService {
    private final VariantRepository variantRepository;
    private final TestRepository testRepository;
    private final VariantConfigRepository variantConfigRepository;
    private final QuestionRepository questionRepository;

    @Transactional
    public void deleteVariant(Integer creator, Integer id) {
        variantRepository.deleteByCreatorIdAndId(creator, id);
    }

    @Transactional
    public void updateVariantSettings(Integer creator, VariantDto settings) {
        Test test = testRepository.findById(settings.getTestId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        if (!test.canEdit(creator))
            throw new ResponseStatusException(FORBIDDEN, "NOT PERMS");
        if (test.isImmutable())
            throw new ResponseStatusException(FORBIDDEN, "IMMUTABLE");

        checkCollisionAndExisting(settings);

        Variant variant = settings.getId() == -1 ?
                new Variant() :
                variantRepository.findById(settings.getId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        variant.setTest(test);
        variant.setNumber(settings.getNumber());
        variantRepository.save(variant);
        updateVariantQuestions(variant, settings.getQuestions());
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

    private void updateVariantQuestions(Variant variant, Map<Integer, Integer> questionsByNumber) {
        List<VariantConfig> configs = new ArrayList<>();

        for (Map.Entry<Integer, Integer> e : questionsByNumber.entrySet()) {
            VariantConfig config =  new VariantConfig();
            config.setNumber(e.getKey());
            Question question = new Question();
            question.setId(e.getValue());
            config.setId(new VariantConfig.Id(variant, question));
            configs.add(config);
        }

        variantConfigRepository.deleteAllById_Variant(variant);
        variantConfigRepository.saveAll(configs);
    }


    public List<Variant> findAll(Integer creator, Integer test) {
        return variantRepository.findAllByCreatorIdAndTestId(creator, test);
    }

    public Variant findVariant(Integer creator, Integer id) {
        return variantRepository.findByCreatorIdAndId(creator, id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
