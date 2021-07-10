package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter @Setter
public class Test {
    @Id
    private Integer id;
    @Enumerated(EnumType.ORDINAL)
    private Discipline discipline;
    private Integer creator;
    private String title;
    private Timestamp timeStart;
    private Timestamp timeEnd;
}
