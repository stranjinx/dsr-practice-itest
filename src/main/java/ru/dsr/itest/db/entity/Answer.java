package ru.dsr.itest.db.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Getter @Setter
public class Answer {
    @EmbeddedId
    private Id id;

    @Embeddable @Getter @AllArgsConstructor
    public static class Id implements Serializable {
        private Integer responseId;
        private Integer choiceId;
        public Id(){}
    }
}
