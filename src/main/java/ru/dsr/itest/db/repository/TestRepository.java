package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.dsr.itest.db.entity.Test;
import ru.dsr.itest.rest.response.CreatedTest;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface TestRepository extends CrudRepository<Test, Integer> {
    @Query(value = "INSERT INTO test (creator,  discipline) VALUES (?1, ?2) RETURNING test.id, test.title, test.discipline",nativeQuery = true)
    CreatedTest create(Integer creatorId, Integer discipline);

    @Query(value = "SELECT id, title, discipline FROM test WHERE creator = ?1", nativeQuery = true)
    List<CreatedTest> findMainByCreatorId(Integer id);

    Optional<Test> findTestByIdAndCreator(Integer id, Integer creator);

    @Transactional
    @Modifying
    @Query("UPDATE Test SET timeStart = ?3, timeEnd = ?4 WHERE creator = ?1 AND id = ?2")
    int startTest(Integer creatorId, Integer id, Timestamp from, Timestamp to);

    @Transactional
    int deleteByIdAndCreator(Integer id, Integer creator);
}
