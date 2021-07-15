package ru.dsr.itest.rest.dto;

import lombok.Getter;
import ru.dsr.itest.db.entity.Student;

import javax.validation.constraints.Size;

@Getter
public class StudentDto {
    @Size(min = 2, max = 64)
    private String firstName;
    @Size(min = 2, max = 64)
    private String middleName;
    @Size(min = 2, max = 64)
    private String lastName;
    @Size(min = 2, max = 256)
    private String university;
    @Size(min = 2, max = 256)
    private String faculty;
    @Size(min = 2, max = 256)
    private String direction;
    @Size(min = 1, max = 7)
    private Integer grade;
    @Size(min = 1)
    private Integer groupNumber;

    public Student toStudent() {
        Student s = new Student();
        s.setFirstName(firstName);
        s.setMiddleName(middleName);
        s.setLastName(lastName);
        s.setDirection(direction);
        s.setGrade(grade);
        s.setGroupNumber(groupNumber);
        s.setUniversity(university);
        s.setFaculty(faculty);
        return s;
    }
}
