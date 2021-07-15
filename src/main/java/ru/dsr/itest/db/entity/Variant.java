package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Getter @Setter
public class Variant {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private Integer testId;
    private Integer number;

    @OneToMany(mappedBy = "configId.variant")
    List<VariantConfig> configs = new ArrayList<>();
}
