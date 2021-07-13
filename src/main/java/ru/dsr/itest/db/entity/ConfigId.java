package ru.dsr.itest.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@Getter
@Setter
public class ConfigId implements Serializable {
    private Integer variant;
    private Integer question;

    public ConfigId() { }
}
