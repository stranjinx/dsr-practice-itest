package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.Account;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.db.repository.TestRepository;
import ru.dsr.itest.rest.dto.TestCreateDto;
import ru.dsr.itest.rest.dto.TestEditDto;
import ru.dsr.itest.rest.dto.TestDurationDto;
import ru.dsr.itest.rest.response.TestView;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;

    public Test createTest(Integer creator, TestCreateDto testData) {
        Test test = testData.toTest();
        Account account = new Account();
        account.setId(creator);
        test.setCreator(creator);
        return testRepository.save(test);
    }

    public List<TestView> findAll(Integer id) {
        return testRepository.findAllByCreator(id);
    }

    public Test findTest(Integer creator, Integer id) {
        return testRepository.findByCreatorAndId(creator, id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public void updateTestSettings(Integer creator, Integer id, TestEditDto testDto) {
        Test test = findTest(creator, id);
        test.setTitle(testDto.getTitle());
        testRepository.save(test);
    }

    @Transactional
    public void start(Integer creator, Integer id, TestDurationDto duration) {
        testRepository.saveDuration(creator, id, duration.getTimeStart(), duration.getTimeEnd());
    }

    @Transactional
    public void stop(Integer creator, Integer id) {
        testRepository.saveDurationTimeEnd(creator, id, Timestamp.from(Instant.now()));
    }

    public void deleteTest(Integer creator, Integer id) {
        testRepository.deleteByCreatorAndId(creator, id);
    }
}
