package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Var;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Getter @Setter
public class Question {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    @ManyToOne
    private Test test;
    private String title;
    private Integer weight;
    @OneToMany(mappedBy = "question")
    private List<Choice> choices = new ArrayList<>();
}
