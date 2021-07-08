package ru.dsr.itest.db.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dsr.itest.db.entity.Account;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
    Optional<Account> findAccountByEmail(String email);

    @Query(value = "INSERT INTO account(email, password) VALUES(?1, ?2) ON CONFLICT DO NOTHING RETURNING *", nativeQuery = true)
    Optional<Account> create(String email, String password);
}
