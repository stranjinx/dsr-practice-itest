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

    @Transactional
    public void pushQuestionSettings(Integer creator, QuestionDto settings) {
        actionValidator.validateUpdateTest(creator, settings.getTestId());

        Question question = settings.getId() == -1 ?
                new Question() :
                questionRepository.findById(settings.getId())
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        question.setTitle(settings.getTitle());
        question.setTestId(settings.getTestId());
        question.setWeight(settings.getWeight());

        questionRepository.save(question);

        pushChanges(
                question.getId(),
                question.getChoices().stream().collect(Collectors.toMap(Choice::getId, c -> c)),
                settings.getChoices()
        );
    }

    private void pushChanges(Integer questionId,
                             Map<Integer, Choice> currentChoicesById,
                             Map<Integer, ChoiceDto> choiceDtoByNumber) {
        List<Choice> updated = new ArrayList<>();
        int incorrectCount = 0;
        for (Map.Entry<Integer, ChoiceDto> o : choiceDtoByNumber.entrySet()) {
            ChoiceDto choiceSettings = o.getValue();

            if (!choiceSettings.isCorrect())
                ++incorrectCount;

            int choiceId = choiceSettings.getId();
            Choice choice;
            if (choiceId == -1)
                choice = new Choice();
            else if ((choice = currentChoicesById.remove(choiceId)) == null)
                throw new ResponseStatusException(NOT_FOUND);

            choice.setQuestionId(questionId);
            choice.setNumber(o.getKey());
            choice.setCorrect(choiceSettings.isCorrect());
            choice.setTitle(choiceSettings.getTitle());
            updated.add(choice);
        }

        if (incorrectCount == updated.size())
            throw new ResponseStatusException(BAD_REQUEST);

        choiceRepository.deleteAll(currentChoicesById.values());
        choiceRepository.saveAll(updated);
    }
}
