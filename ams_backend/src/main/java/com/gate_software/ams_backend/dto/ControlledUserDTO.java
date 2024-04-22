package com.gate_software.ams_backend.dto;

import lombok.Data;

import java.util.List;

@Data
public class ControlledUserDTO {
    private String name;
    private String email;
    private String password;
    private String presence;
    private float salary;
    private int jobId;
    private List<ControlledUserScheduleDTO> schedules;
}
