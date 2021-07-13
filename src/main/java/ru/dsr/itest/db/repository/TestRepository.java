package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.rest.response.TestView;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends CrudRepository<Test, Integer> {
    @Query(value = "SELECT id, title, discipline FROM test WHERE creator = ?1", nativeQuery = true)
    List<TestView> findAllByCreatorId(Integer id);

    @Query(nativeQuery = true,
            value = "SELECT t.* FROM question q, test t " +
                    "WHERE q.id = :question AND t.id = q.test_id")
    Optional<Test> findByQuestionId(Integer question);

    @Query(nativeQuery = true,
            value = "SELECT t.* FROM variant v, test t " +
                    "WHERE v.id = :variant AND t.id = v.test_id")
    Optional<Test> findByVariantId(Integer variant);

    int deleteByIdAndCreator(Integer id, Integer creator);
}
