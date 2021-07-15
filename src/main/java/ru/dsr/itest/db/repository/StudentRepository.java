package ru.dsr.itest.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dsr.itest.db.entity.Student;

public interface StudentRepository extends CrudRepository<Student, Integer> {

}
