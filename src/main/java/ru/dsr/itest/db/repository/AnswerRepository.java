package ru.dsr.itest.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Answer;

import java.util.Collection;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Answer.Id> {
}
