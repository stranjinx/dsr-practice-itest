package ru.dsr.itest.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dsr.itest.db.entity.Variant;
import ru.dsr.itest.db.entity.VariantConfig;


public interface VariantConfigRepository extends CrudRepository<VariantConfig, VariantConfig.Id> {
    void deleteAllById_Variant(Variant variant);
}
