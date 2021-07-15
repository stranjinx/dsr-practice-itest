package ru.dsr.itest.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dsr.itest.db.entity.ConfigId;
import ru.dsr.itest.db.entity.VariantConfig;

import java.util.List;

public interface VariantConfigRepository extends CrudRepository<VariantConfig, ConfigId> {
    List<VariantConfig> findAllByConfigId_Variant(Integer variant);
    void deleteAllByConfigId_Variant(Integer variant);
}
