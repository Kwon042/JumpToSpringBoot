package com.example.sbb.user;

import lombok.Getter;

// ADMIN 과 USER 상수는 값을 변경할 필요가 없으니 Setter 는 필요 없음
@Getter
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value) {
        this.value = value;
    }

    private String value;
}
