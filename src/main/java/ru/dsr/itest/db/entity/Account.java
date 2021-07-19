package ru.dsr.itest.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.*;

@Entity @Getter @Setter
public class Account {
    @Id
    @GeneratedValue(strategy = IDENTITY)
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
