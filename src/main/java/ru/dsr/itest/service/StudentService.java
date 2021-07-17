package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dsr.itest.db.entity.Student;
import ru.dsr.itest.db.repository.StudentRepository;
import ru.dsr.itest.rest.dto.StudentDto;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    public void updateStudentProfile(Integer account, StudentDto dto) {
        Student student = dto.toStudent();
        student.setAccountId(account);
        repository.save(student);
    }

    public Student getStudentProfile(Integer account) {
        return repository.findById(account).orElse(new Student());
    }
}
