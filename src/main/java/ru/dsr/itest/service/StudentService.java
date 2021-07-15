package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.Student;
import ru.dsr.itest.db.repository.StudentRepository;
import ru.dsr.itest.rest.dto.StudentDto;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;
    public void updateProfile(Integer account, StudentDto dto) {
        Student student = dto.toStudent();
        student.setAccountId(account);
        repository.save(student);
    }

    public Student getProfile(Integer account) {
        return repository.findById(account)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
    }
}
