package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.Question;
import ru.dsr.itest.db.entity.Variant;
import ru.dsr.itest.db.entity.VariantConfig;
import ru.dsr.itest.db.repository.QuestionRepository;
import ru.dsr.itest.db.repository.VariantConfigRepository;
import ru.dsr.itest.db.repository.VariantRepository;
import ru.dsr.itest.rest.dto.VariantDto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public void pushVariantSettings(Integer creator, VariantDto settings) {
        actionValidator.validateUpdateTest(creator, settings.getTestId());
        if (!questionRepository.existsAllByTestId(
                settings.getTestId(),
                settings.getQuestions().values(),
                settings.getQuestions().size()))
            throw new ResponseStatusException(BAD_REQUEST);

        Variant variant = settings.getId() == -1 ?
                new Variant() :
                variantRepository.findById(settings.getId())
                    .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        variant.setTestId(settings.getTestId());
        variant.setNumber(settings.getNumber());

        List<VariantConfig> target = variantConfigRepository.findAllByConfigId_Variant(variant.getId());
        pushChanges(variant.getId(), target, settings.getQuestions(), settings.getDeletedQuestions());
        variantRepository.save(variant);
        for (VariantConfig config : target) {
            config.getConfigId().setVariant(variant.getId());
        }
        variantConfigRepository.saveAll(target);
        if (settings.getDeletedQuestions().isEmpty())
            return;

        variantConfigRepository.deleteAllByQuestionsAndVariant(settings.getDeletedQuestions(), variant.getNumber());
    }

    private void pushChanges(Integer variant,
                             List<VariantConfig> target,
                             Map<Integer, Integer> toUpdate,
                             List<Integer> toDelete) {
        Map<Integer, VariantConfig> targetMap = target
                .stream()
                .collect(Collectors.toMap(VariantConfig::getNumber, vc -> vc));
        target.clear();
        for (Map.Entry<Integer, Integer> e : toUpdate.entrySet()) {
            int number = e.getKey();
            int questionId = e.getValue();
            if (toDelete.contains(e.getValue()))
                throw  new ResponseStatusException(BAD_REQUEST);
            VariantConfig config = targetMap.computeIfAbsent(number, (n) -> new VariantConfig());
            config.getConfigId().setQuestion(questionId);
            config.setNumber(number);
            target.add(config);
        }
    }


    public List<Variant> findAll(Integer creator, Integer test) {
        actionValidator.validateGetTest(creator, test);
        return variantRepository.findAllByTestId(test);
    }

    public List<VariantConfig> findVariant(Integer creator, Integer id) {
        actionValidator.validateGetVariant(creator, id);
        return variantConfigRepository.findAllByConfigId_Variant(id);
    }
}
