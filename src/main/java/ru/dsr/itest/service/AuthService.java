package ru.dsr.itest.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dsr.itest.db.entity.Account;
import ru.dsr.itest.db.repository.AccountRepository;
import ru.dsr.itest.rest.dto.AuthDto;

import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder encoder;

    public Account register(AuthDto authDto) {
        String encodedPass = encoder.encode(authDto.getPassword());
        Optional<Account> created =  accountRepository.create(authDto.getEmail(), encodedPass);
        if (!created.isPresent())
            throw new ResponseStatusException(UNAUTHORIZED, "EMAIL EXISTS");
        return created.get();
    }

    public Account login(AuthDto authDto) {
        Optional<Account> account =  accountRepository.findAccountByEmail(authDto.getEmail());
        if (!account.isPresent())
            throw new ResponseStatusException(UNAUTHORIZED, "WRONG EMAIL");
        if (!validate(authDto, account.get()))
            throw new ResponseStatusException(UNAUTHORIZED, "WRONG PASSWORD");
        return account.get();
    }

    private boolean validate(AuthDto signIn, Account account) {
        return encoder.matches(signIn.getPassword(), account.getPassword());
    }
}
