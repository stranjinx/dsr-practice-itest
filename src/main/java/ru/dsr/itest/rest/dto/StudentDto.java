package ru.dsr.itest.rest.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;
import ru.dsr.itest.db.entity.Student;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
    @Min(1) @Max(7)
    private Integer grade;
    @Min(1)
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
