package ru.dsr.itest.db.entity;

import lombok.Getter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Entity @Getter
public class Response {
    @Id
    private Integer id;
    private Integer respondentId;
    @ManyToOne
    private Test test;
    @ManyToOne
    private Variant variant;
    @OneToMany(mappedBy = "id.responseId")
    private List<Answer> answers;

    private Timestamp timeStart;
    private Timestamp timeEnd;

    public boolean isCompleted() {
        return Timestamp.from(Instant.now()).after(test.getTimeEnd()) ||
                timeStart.before(test.getTimeStart());
    }
}
