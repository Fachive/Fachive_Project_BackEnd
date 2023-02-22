package com.facaieve.backend.Constant;

import lombok.Getter;

public enum UserRole {

    GENERAL("GENERAL"),
    ADMIN("ADMIN");

    @Getter
    private String userRole;

    UserRole(String role){
        this.userRole = role;
    }
}
