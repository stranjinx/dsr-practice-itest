package ru.dsr.itest.db.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Getter
public class TestHistory {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private Integer testId;
    private Timestamp timeStart;
    private Timestamp timeEnd;
}
