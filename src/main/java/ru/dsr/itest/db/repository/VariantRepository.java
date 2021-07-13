package ru.dsr.itest.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Variant;

import java.util.List;

@Repository
public interface VariantRepository extends CrudRepository<Variant, Integer> {
    List<Variant> findAllByTestId(Integer test);
}
