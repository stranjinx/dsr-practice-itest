package ru.dsr.itest.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity @Getter @Setter
public class VariantConfig implements Serializable {
    @EmbeddedId
    private Id id = new Id();
    private Integer number;

    @Embeddable
    @AllArgsConstructor @Getter @Setter
    public static class Id implements Serializable {
        @ManyToOne
        private Variant variant;
        @ManyToOne
        private Question question;

        public Id() { }
    }
}
