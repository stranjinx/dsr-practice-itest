package ru.dsr.itest.db.repository;

import org.springframework.data.repository.CrudRepository;
import ru.dsr.itest.db.entity.Choice;

public interface ChoiceRepository extends CrudRepository<Choice, Integer> {
}
