package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.db.repository.TestRepository;
import ru.dsr.itest.rest.dto.TestCreateDto;
import ru.dsr.itest.rest.dto.TestEditDto;
import ru.dsr.itest.rest.dto.TestHistoryDto;
import ru.dsr.itest.rest.response.TestView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final ActionValidator actionValidator;

    public Test createTest(Integer creator, TestCreateDto testData) {
        Test test = testData.toTest();
        test.setCreator(creator);
        return testRepository.save(test);
    }

    public List<TestView> findAll(Integer id) {
        return testRepository.findAllByCreatorId(id);
    }

    public Test findTest(Integer creator, Integer id) {
        return actionValidator.validateAndGetTest(creator, testRepository.findById(id));
    }

    public void updateTestSettings(Integer creator, Integer id, TestEditDto testDto) {
        Test test = findTest(creator, id);
        test.setTitle(testDto.getTitle());
        testRepository.save(test);
    }

    @Transactional
    public void start(Integer creator, Integer id, TestHistoryDto duration) {
        actionValidator.validateStartTest(creator, id);
        testRepository.saveDuration(id, duration.getTimeStart(), duration.getTimeEnd());
    }

    @Transactional
    public void stop(Integer creator, Integer id) {
        actionValidator.validateStopTest(creator, id);
        testRepository.saveDurationTimeEnd(id, Timestamp.from(Instant.now()));
    }

    public void deleteTest(Integer creator, Integer id) {
        actionValidator.validateDeleteTest(creator, id);
        testRepository.deleteById(id);
    }
}
