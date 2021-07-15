package ru.dsr.itest.security.details;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.dsr.itest.rest.dto.TestExamDto;

import java.util.Collection;

public class AccountDetails implements UserDetails {
    @Getter
    private final Integer id;
    @Getter
    private final String email;
    private final String pass;
    private final Collection<? extends GrantedAuthority> grantedAuthorities;

    public AccountDetails(Integer id, String email, String pass, Collection<? extends GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.email = email;
        this.pass = pass;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return pass;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
