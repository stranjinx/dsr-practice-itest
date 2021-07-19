package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Discipline;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.rest.dto.TestResponseFormDto;
import ru.dsr.itest.rest.response.RatingView;
import ru.dsr.itest.rest.response.TestExamInfoView;
import ru.dsr.itest.rest.response.TestView;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends CrudRepository<Test, Integer> {
    List<TestView> findAllByCreator(Integer creator);

    @Query(value =
            "SELECT t.id, t.title, t.discipline, t.time_start as timeStart, t.time_end as timeEnd FROM test t WHERE t.discipline = ?1 AND " +
            "t.time_start < now() AND (t.time_end IS NULL OR now() < t.time_end)",
            nativeQuery = true)
    List<TestExamInfoView> findAllAvailableByDiscipline(Integer discipline);

    @Query(value =
            "SELECT t.id, t.title, t.discipline, t.time_start as timeStart, t.time_end as timeEnd FROM test t WHERE " +
            "t.time_start < now() AND (t.time_end IS NULL OR now() < t.time_end)",
            nativeQuery = true)
    List<TestExamInfoView> findAllAvailable();

    @Modifying
    @Query("UPDATE Test SET timeStart = :timeStart, timeEnd = :timeEnd " +
            "WHERE id = :id AND creator = :creator " +
            "AND (timeEnd IS NULL AND timeStart IS NULL) OR timeEnd < current_timestamp")
    void saveDuration(Integer creator, Integer id, Timestamp timeStart, Timestamp timeEnd);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE test SET time_end = :timeEnd " +
                    "WHERE id = :id AND creator = :creator " +
                    "AND time_start IS NOT NULL AND time_end IS NULL")
    void saveDurationTimeEnd(Integer creator, Integer id, Timestamp timeEnd);

    void deleteByCreatorAndId(Integer creator, Integer id);

    Optional<Test> findByCreatorAndId(Integer creator, Integer id);

}
