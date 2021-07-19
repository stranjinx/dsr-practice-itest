package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Getter @Setter
public class Choice {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @ManyToOne
    private Question question;
    private Integer number;
    private String title;
    private Boolean correct;

}
