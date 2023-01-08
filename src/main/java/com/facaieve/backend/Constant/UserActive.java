package com.facaieve.backend.Constant;

import lombok.Getter;

public enum UserActive {
    Active("활동 회원"),
    UnActive("휴면 회원"),
    Withdrawal("탈퇴 회원");

    @Getter
    private final String isActive;


    UserActive(String isActive) {
        this.isActive =isActive;
    }
}
