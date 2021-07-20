package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.Choice;
import ru.dsr.itest.db.entity.Question;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.db.repository.ChoiceRepository;
import ru.dsr.itest.db.repository.QuestionRepository;
import ru.dsr.itest.db.repository.TestRepository;
import ru.dsr.itest.rest.dto.ChoiceDto;
import ru.dsr.itest.rest.dto.QuestionDto;
import ru.dsr.itest.rest.response.QuestionView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final TestService testService;
    private final ChoiceRepository choiceRepository;

    public Question getQuest(Integer creator, Integer id) {
        return questionRepository.findByCreatorIdAndId(creator, id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }

    @Transactional
    public void deleteQuest(Integer creator, Integer id) {
        questionRepository.deleteByCreatorIdAndId(creator, id);
    }

    public List<QuestionView> findAll(Integer creator, Integer test) {
        return questionRepository.findAllByCreatorIdAndTestId(creator, test);
    }

    @Transactional
    public void updateQuestionSettings(Integer creator, QuestionDto settings) {
        Test test = testService.findTest(creator, settings.getTestId());
        if (test.isImmutable())
            throw new ResponseStatusException(FORBIDDEN, "IMMUTABLE");

        Question question = settings.getId() == -1 ?
                new Question() :
                questionRepository.findById(settings.getId())
                        .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

        question.setTitle(settings.getTitle());
        question.setTest(test);
        question.setWeight(settings.getWeight());

        questionRepository.save(question);

        updateChoices(
                question,
                question.getChoices().stream().collect(Collectors.toMap(Choice::getId, c -> c)),
                settings.getChoices()
        );
    }

    private void updateChoices(Question question,
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

            choice.setQuestion(question);
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
