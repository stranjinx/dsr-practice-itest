package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Discipline;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.rest.dto.TestExamInfoDto;
import ru.dsr.itest.rest.response.TestView;

import java.sql.Timestamp;
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

    @Query("SELECT t.id, t.title, t.timeStart, t.timeEnd FROM Test t WHERE t.discipline = ?1 AND " +
            "t.timeStart < current_timestamp AND (t.timeEnd IS NULL OR current_timestamp < t.timeEnd)")
    List<TestExamInfoDto> findAllAvailableByDiscipline(Discipline discipline);

    @Query("SELECT t.id, t.title, t.timeStart, t.timeEnd FROM Test t WHERE " +
            "t.timeStart < current_timestamp AND (t.timeEnd IS NULL OR current_timestamp < t.timeEnd)")
    List<TestExamInfoDto> findAllAvailable();

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE test SET time_start = :from, time_end = :to WHERE id = :id")
    void saveDuration(Integer id, Timestamp from, Timestamp to);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE test SET time_end = :timeEnd WHERE id = :id AND time_end IS NULL")
    void saveDurationTimeEnd(Integer id, Timestamp timeEnd);
}
