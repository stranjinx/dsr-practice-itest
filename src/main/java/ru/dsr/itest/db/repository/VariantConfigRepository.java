package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.dsr.itest.db.entity.ConfigId;
import ru.dsr.itest.db.entity.VariantConfig;

import java.util.Collection;
import java.util.List;

public interface VariantConfigRepository extends CrudRepository<VariantConfig, ConfigId> {
    @Modifying
    @Query(nativeQuery = true, value =
            "DELETE FROM variant_config WHERE question IN :list AND variant = :variant")
    void deleteAllByQuestionsAndVariant(Collection<Integer> list, Integer variant);


    List<VariantConfig> findAllByConfigId_Variant(Integer id);
}
