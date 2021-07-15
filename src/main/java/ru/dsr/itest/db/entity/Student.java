package ru.dsr.itest.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Setter
@Getter
public class Student {
    @Id
    private Integer accountId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String university;
    private String faculty;
    private String direction;
    private Integer grade;
    private Integer groupNumber;
}
