package ru.dsr.itest.rest.response;

public interface RatingView {
    Integer getRespondentId();
    Integer getVariantId();
    String getFirstName();
    String getLastName();
    Double getBall();
    Double getPercent();
    Double getMaxBall();
}
