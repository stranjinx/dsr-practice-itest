package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.dsr.itest.db.entity.TestHistory;

import java.sql.Timestamp;

public interface TestHistoryRepository extends CrudRepository<TestHistory, Integer> {
    @Modifying
    @Query(nativeQuery = true,
            value = "INSERT INTO test_history(test_id, time_start, time_end) VALUES(:id, :from, :to)")
    void saveDuration(Integer id, Timestamp from, Timestamp to);

    @Modifying
    @Query(nativeQuery = true,
            value = "UPDATE test_history SET time_end = :timeEnd WHERE id = :id AND time_end IS NULL")
    void saveDurationTimeEnd(Integer id, Timestamp timeEnd);

    boolean existsByTestId(Integer id);

    @Query(nativeQuery = true, value =
            "SELECT EXISTS(SELECT id FROM test_history WHERE test_id = :test AND time_end IS NULL OR time_end < NOW())")
    boolean existsStartedByTestId(Integer test);
}
