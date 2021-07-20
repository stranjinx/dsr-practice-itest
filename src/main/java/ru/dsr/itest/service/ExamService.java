package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.*;
import ru.dsr.itest.db.repository.AnswerRepository;
import ru.dsr.itest.db.repository.ResponseRepository;
import ru.dsr.itest.db.repository.TestRepository;
import ru.dsr.itest.rest.dto.AnswersDto;
import ru.dsr.itest.rest.response.TestExamInfoView;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExamService {
    private final TestRepository testRepository;
    private final ResponseRepository responseRepository;
    private final AnswerRepository answerRepository;

    public List<TestExamInfoView> getAllAvailable(Discipline discipline) {
        return testRepository.findAllAvailableByDiscipline(discipline.ordinal());
    }

    public List<TestExamInfoView> getAllAvailable() {
        return testRepository.findAllAvailable();
    }

    public Response findResponse(Integer respondent, Integer test) {
        return responseRepository.findOrCreateResponseByRespondentIdAndTestId(respondent, test)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void saveResponseAnswers(Integer respondent, Integer test, AnswersDto answers) {
        Response response = findResponse(respondent, test);
        if (response.isCompleted()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "ALREADY COMPLETED");
        }
        List<Answer> toDelete = new ArrayList<>();
        List<Answer> toSave = new ArrayList<>();
        for (VariantConfig config : response.getVariant().getConfigs()) {
            Question q = config.getId().getQuestion();
            boolean isOneAnswer = q.getChoices().stream().filter(Choice::getCorrect).count() == 1;
            int selected = 0;
            for (Choice choice : q.getChoices()) {
                Answer a = new Answer();
                a.setId(new Answer.Id(response.getId(), choice.getId()));
                if (answers.getChoiceIdList().contains(choice.getId())) {
                    ++selected;
                    toSave.add(a);
                } else {
                    toDelete.add(a);
                }
            }
            if (isOneAnswer && selected > 1) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }
        answerRepository.deleteAll(toDelete);
        answerRepository.saveAll(toSave);
        responseRepository.updateLastSaveTimeById(response.getId());
    }
}
