package ru.dsr.itest.db.entity;

public enum Role {
    STUDENT, CREATOR;

    public String getRoleString() {
        return "ROLE_" + toString();
    }
}
