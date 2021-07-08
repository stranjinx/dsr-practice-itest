package ru.dsr.itest.security.component;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import ru.dsr.itest.db.entity.Account;

import java.util.Collection;
import java.util.Collections;

@Component
public class AuthorityBuilder {
    public Collection<? extends GrantedAuthority> fromAccount(Account account) {
        return Collections.singleton(new SimpleGrantedAuthority(account.getRole().getRoleString()));
    }
}
