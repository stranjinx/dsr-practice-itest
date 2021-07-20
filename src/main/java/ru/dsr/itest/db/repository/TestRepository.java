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
            "t.time_start < now() AND now() < t.time_end",
            nativeQuery = true)
    List<TestExamInfoView> findAllAvailableByDiscipline(Integer discipline);

    @Query(value =
            "SELECT t.id, t.title, t.discipline, t.time_start as timeStart, t.time_end as timeEnd FROM test t WHERE " +
            "t.time_start < now() AND now() < t.time_end",
            nativeQuery = true)
    List<TestExamInfoView> findAllAvailable();

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE test SET time_end = NOW() " +
                    "WHERE id = :id AND creator = :creator " +
                    "AND time_start IS NOT NULL")
    void stopTest(Integer creator, Integer id);

    void deleteByCreatorAndId(Integer creator, Integer id);

    Optional<Test> findByCreatorAndId(Integer creator, Integer id);

}
