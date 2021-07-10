package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dsr.itest.db.entity.Discipline;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.db.repository.TestRepository;
import ru.dsr.itest.rest.exception.EntityNotFoundException;
import ru.dsr.itest.rest.request.TestDuration;
import ru.dsr.itest.rest.request.UpdateTest;
import ru.dsr.itest.rest.response.CreatedTest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository repository;

    public CreatedTest createTest(Integer creator, Discipline discipline) {
        return repository.create(creator, discipline.ordinal());
    }

    public List<CreatedTest> findMain(Integer id) {
        return repository.findMainByCreatorId(id);
    }

    public Test findTest(Integer creator, Integer id) {
        return repository.findTestByIdAndCreator(id, creator)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void updateTest(Integer creator, Integer id, UpdateTest updateData) {
        Test test = findTest(creator, id);
        putChanges(test, updateData);
        repository.save(test);
    }

    private void putChanges(Test test, UpdateTest updateData) {
        test.setTitle(updateData.getTitle());
    }

    public void startTest(Integer creator, Integer id, TestDuration duration) {
        int updated = repository.startTest(creator, id, duration.getTimeStart(), duration.getTimeEnd());
        if (updated < 1) {
            throw new EntityNotFoundException();
        }
    }

    public void deleteTest(Integer creator, Integer id) {
        int deleted = repository.deleteByIdAndCreator(id, creator);
        if (deleted < 1) {
            throw new EntityNotFoundException();
        }
    }
}
