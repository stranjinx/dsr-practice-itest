package ru.dsr.itest.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity @Getter
public class Account {
    @Id
    private Integer id;
    private String email;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private Role role;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Account() {}
}
