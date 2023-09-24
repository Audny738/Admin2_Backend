package com.gate_software.ams_backend.dto;

public class AuthenticatedUser {
    private Integer id;
    private String userType;

    public AuthenticatedUser(Integer id, String userType) {
        this.id = id;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }
}
