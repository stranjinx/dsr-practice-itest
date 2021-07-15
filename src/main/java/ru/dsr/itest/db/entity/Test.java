package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter @Setter
public class Test {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @Enumerated(EnumType.ORDINAL)
    private Discipline discipline;
    private Integer creator;
    private String title;

    @OneToMany(mappedBy = "testId", fetch = LAZY)
    private List<TestHistory> history = new ArrayList<>();
}
