package com.gate_software.ams_backend.dto;

import lombok.Data;

@Data
public class AuthenticatedUserDTO {
    private Integer id;
    private String userType;

    public AuthenticatedUserDTO(Integer id, String userType) {
        this.id = id;
        this.userType = userType;
    }
}
