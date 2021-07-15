package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.db.repository.TestRepository;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.springframework.http.HttpStatus.*;

@Component
@RequiredArgsConstructor
public class ActionValidator {
    private final TestRepository testRepository;

    //GET ACTION
    public void validateGetVariant(Integer creator, Integer variant) {
        validateAndGetTest(creator, testRepository.findByVariantId(variant));
    }

    public void validateGetQuestion(Integer account, Integer question) {
        validateAndGetTest(account, testRepository.findByQuestionId(question));
    }

    public void validateGetTest(Integer account, Integer test) {
        validateAndGetTest(account, testRepository.findById(test));
    }

    //DELETE ACTION
    public void validateDeleteVariant(Integer creator, Integer variant) {
        validateUpdate(creator, testRepository.findByVariantId(variant));
    }

    public void validateDeleteQuestion(Integer account, Integer question) {
        validateUpdate(account, testRepository.findByQuestionId(question));
    }

    public void validateDeleteTest(Integer account, Integer test) {
        validateAndGetTest(account, testRepository.findById(test));
    }

    //UPDATE ACTION
    public Test validateAndGetTest(Integer account, Optional<Test> test) {
        if (!test.isPresent())
            throw new ResponseStatusException(NOT_FOUND);
        if (!test.get().getCreator().equals(account))
            throw new ResponseStatusException(FORBIDDEN, "NOT PERMS");
        return test.get();
    }

    public void validateUpdateTest(Integer account, Integer test) {
        validateUpdate(account, testRepository.findById(test));
    }

    public void validateUpdate(Integer account, Optional<Test> test) {
        if (!test.isPresent())
            throw new ResponseStatusException(NOT_FOUND);
        if (!test.get().getCreator().equals(account))
            throw new ResponseStatusException(FORBIDDEN, "NOT PERMS");
        if (test.get().getTimeStart() != null)
            throw new ResponseStatusException(FORBIDDEN,"FINAL STATE");
    }

    public void validateStartTest(Integer account, Integer testId) {
        Test test = validateExistingAndPermission(account, testId);
        if (test.getTimeEnd() == null || test.getTimeEnd().after(Date.from(Instant.now())))
            throw new ResponseStatusException(FORBIDDEN, "ALREADY STARTED");
    }

    public void validateStopTest(Integer account, Integer testId) {
        Test test = validateExistingAndPermission(account, testId);
        if (test.getTimeEnd() == null || test.getTimeEnd().after(Date.from(Instant.now())))
            throw new ResponseStatusException(NOT_FOUND);
    }

    //OTHER
    private Test validateExistingAndPermission(Integer account, Integer testId) {
        if (testId == null)
            throw new ResponseStatusException(NOT_FOUND);
        Optional<Test> test =  testRepository.findById(testId);
        if (!test.isPresent())
            throw new ResponseStatusException(NOT_FOUND);
        if (!test.get().getCreator().equals(account))
            throw new ResponseStatusException(FORBIDDEN, "NOT PERMS");
        return test.get();
    }
}
