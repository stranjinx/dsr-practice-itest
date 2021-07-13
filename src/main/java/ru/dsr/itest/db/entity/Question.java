package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;
import org.aspectj.weaver.ast.Var;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity @Getter @Setter
public class Question {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Integer id;
    private Integer testId;
    private String title;
    private Integer weight;
    @OneToMany(mappedBy = "questionId")
    private List<Choice> choices = new ArrayList<>();
}
