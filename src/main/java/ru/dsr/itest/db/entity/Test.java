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
    private Timestamp timeStart;
    private Timestamp timeEnd;

    public boolean canEdit(Integer creator) {
        return this.creator.equals(creator);
    }

    public boolean isImmutable() {
        return timeStart != null;
    }
}
