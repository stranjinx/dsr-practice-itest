package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dsr.itest.db.entity.Account;
import ru.dsr.itest.db.repository.AccountRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public Optional<Account> findByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }
}
