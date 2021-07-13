package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity @Getter @Setter
public class VariantConfig implements Serializable {
    @EmbeddedId
    private ConfigId configId = new ConfigId();
    private Integer number;
}
