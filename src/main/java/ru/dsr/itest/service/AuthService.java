package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dsr.itest.db.entity.Account;
import ru.dsr.itest.db.repository.AccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;

    public  Optional<Account> register(Account account) {
        return accountRepository.create(account.getEmail(), account.getPassword());
    }

    private boolean equals(Account one, Account two) {
        return one.getEmail().equals(two.getEmail());
    }

    public Optional<Account> findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }
}
