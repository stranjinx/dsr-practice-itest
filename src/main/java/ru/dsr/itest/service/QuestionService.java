package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.Choice;
import ru.dsr.itest.db.entity.Question;
import ru.dsr.itest.db.repository.ChoiceRepository;
import ru.dsr.itest.db.repository.QuestionRepository;
import ru.dsr.itest.rest.dto.ChoiceDto;
import ru.dsr.itest.rest.dto.QuestionDto;
import ru.dsr.itest.rest.response.QuestionView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final ChoiceRepository choiceRepository;
    private final ActionValidator actionValidator;

    public Question getQuest(Integer creator, Integer id) {
        actionValidator.validateGetQuestion(creator, id);
        return questionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Transactional
    public void deleteQuest(Integer creator, Integer id) {
        actionValidator.validateDeleteQuestion(creator, id);
        questionRepository.deleteById(id);
    }

    public List<QuestionView> findAll(Integer creator, Integer test) {
        actionValidator.validateGetTest(creator, test);
        return questionRepository.findAllByTestId(test);
    }

    public void pushQuestionSettings(Integer creator, QuestionDto settings) {
        actionValidator.validateUpdateTest(creator, settings.getTestId());

        Question question = settings.getId() == -1 ?
                new Question() :
                questionRepository.findById(settings.getId())
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        question.setTitle(settings.getTitle());
        question.setTestId(settings.getTestId());
        question.setWeight(settings.getWeight());

        List<Choice> choices = new ArrayList<>(question.getChoices());
        pushChanges(question.getId(), choices, settings.getChoices(), settings.getDeletedChoices());

        questionRepository.save(question);
        choiceRepository.saveAll(choices);
        choiceRepository.deleteAllById(settings.getDeletedChoices());
    }

    private void pushChanges(Integer questionId,
                             List<Choice> target,
                             Map<Integer, ChoiceDto> toUpdate,
                             List<Integer> toDelete) {
        Map<Integer, Choice> targetMap = target.stream().collect(Collectors.toMap(Choice::getId, c -> c));
        target.clear();

        int incorrectCount = 0;
        for (Map.Entry<Integer, ChoiceDto> o : toUpdate.entrySet()) {
            ChoiceDto choiceDataToUpdate = o.getValue();

            if (toDelete.contains(choiceDataToUpdate.getId()))
                throw new ResponseStatusException(BAD_REQUEST);

            if (!choiceDataToUpdate.isCorrect())
                ++incorrectCount;

            int choiceId = choiceDataToUpdate.getId();
            Choice choiceToUpdate;

            if (choiceId == -1) {
                choiceToUpdate = new Choice();
            } else {
                choiceToUpdate = targetMap.get(choiceId);
                if (choiceToUpdate == null)
                    throw new ResponseStatusException(NOT_FOUND);
            }

            choiceToUpdate.setQuestionId(questionId);
            choiceToUpdate.setNumber(o.getKey());
            choiceToUpdate.setCorrect(choiceDataToUpdate.isCorrect());
            choiceToUpdate.setTitle(choiceDataToUpdate.getTitle());
            target.add(choiceToUpdate);
        }

        if (incorrectCount == toUpdate.size())
            throw new ResponseStatusException(BAD_REQUEST);
    }
}
