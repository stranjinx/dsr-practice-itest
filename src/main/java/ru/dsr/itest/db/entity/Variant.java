package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Getter @Setter
public class Variant {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @ManyToOne
    private Test test;
    private Integer number;

    @OneToMany(mappedBy = "id.variant")
    List<VariantConfig> configs = new ArrayList<>();
}
