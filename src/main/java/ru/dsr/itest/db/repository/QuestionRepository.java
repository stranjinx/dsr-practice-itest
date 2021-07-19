package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Question;
import ru.dsr.itest.rest.response.QuestionView;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {
    @Query(value =
            "SELECT q.id, q.title, q.weight FROM question q, test t " +
            "WHERE q.test_id = :test AND t.id = :test AND t.creator = :creator",
            nativeQuery = true)
    List<QuestionView> findAllByCreatorIdAndTestId(Integer creator, Integer test);

    @Query(nativeQuery = true,
            value = "SELECT COUNT(q.*) = :size FROM question q, test t WHERE q.id in :values AND q.test_id = :testId" )
    boolean existsAllByTestId(Integer testId, Collection<Integer> values, Integer size);

    @Query(nativeQuery = true, value =
            "SELECT * FROM question q, test t WHERE q.id = :id AND q.test_id = t.id AND t.creator = :creator")
    Optional<Question> findByCreatorIdAndId(Integer creator, Integer id);

    @Modifying
    @Query(nativeQuery = true, value =
            "DELETE FROM question q WHERE q.id = :id AND " +
                    "EXISTS(SELECT * FROM test t WHERE q.test_id = t.id AND t.creator = :creator AND t.time_start IS NULL)")
    void deleteByCreatorIdAndId(Integer creator, Integer id);
}
