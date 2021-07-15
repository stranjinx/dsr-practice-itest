package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Question;
import ru.dsr.itest.rest.response.QuestionView;

import java.util.Collection;
import java.util.List;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query(value = "SELECT q.id, q.title, q.weight FROM question q WHERE q.test_id = :testId",
            nativeQuery = true)
    List<QuestionView> findAllByTestId(Integer testId);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(q.*) = :size FROM question q, test t WHERE q.id in :values AND q.test_id = :testId" )
    boolean existsAllByTestId(Integer testId, Collection<Integer> values, Integer size);
}
