package com.gate_software.ams_backend.dto;

public class AuthenticatedUserDTO {
    private Integer id;
    private String userType;

    public AuthenticatedUserDTO(Integer id, String userType) {
        this.id = id;
        this.userType = userType;
    }

    public Integer getId() {
        return this.id;
    }

    public String getUserType() {
        return this.userType;
    }
}
