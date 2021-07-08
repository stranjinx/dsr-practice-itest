package ru.dsr.itest.security.component;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.dsr.itest.db.entity.Account;
import ru.dsr.itest.security.details.AccountDetails;
import ru.dsr.itest.service.AccountService;

import java.util.Collection;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountDetailsService implements UserDetailsService {
    private final AccountService userService;
    private final AuthorityBuilder authorityBuilder;

    @Override
    public AccountDetails loadUserByUsername(String email) {
        Optional<Account> op = userService.findByEmail(email);
        if (!op.isPresent()) return null;
        Account account = op.get();
        Collection<? extends GrantedAuthority> authorities = authorityBuilder.fromAccount(account);
        return new AccountDetails(account.getId(), account.getEmail(), account.getPassword(), authorities);
    }
}
