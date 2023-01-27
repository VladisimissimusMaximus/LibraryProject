package com.company.model;

public enum UserRole {
    READER(1), LIBRARIAN(2), ADMINISTRATOR(3);

    private final Integer id;

    UserRole(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public static UserRole byId(int id) {
        return values()[id-1];
    }
}
