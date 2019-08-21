package com.yorix.autometer.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, POWER, ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
