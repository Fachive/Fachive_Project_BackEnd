package com.facaieve.backend.Constant;

import lombok.Getter;

public enum UserRole {

    GENERAL("general_user"),
    ADMIN("admin_user");

    @Getter
    private String userRole;

    UserRole(String role){
        this.userRole = role;
    }

}
