package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Variant;

import java.util.List;
import java.util.Optional;

@Repository
public interface VariantRepository extends CrudRepository<Variant, Integer> {
    @Query(nativeQuery = true, value =
            "SELECT v.* FROM variant v, test t WHERE v.id = :id AND v.test_id = t.id AND t.creator = :creator")
    Optional<Variant> findByCreatorIdAndId(Integer creator, Integer id);

    @Query(nativeQuery = true, value =
            "SELECT v.* FROM variant v, test t WHERE v.test_id = :test AND t.id = :test AND t.creator = :creator")
    List<Variant> findAllByCreatorIdAndTestId(Integer creator, Integer test);

    @Query(nativeQuery = true, value =
            "DELETE FROM variant v WHERE v.id = :id AND " +
                    "EXISTS(SELECT * FROM test t WHERE v.test_id = t.id AND t.creator = :creator AND t.time_start IS NULL)")
    void deleteByCreatorIdAndId(Integer creator, Integer id);
}
