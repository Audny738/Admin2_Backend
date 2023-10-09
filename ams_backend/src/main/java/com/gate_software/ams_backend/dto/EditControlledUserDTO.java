package com.gate_software.ams_backend.dto;

import com.gate_software.ams_backend.entity.Schedule;
import lombok.Data;

import java.util.List;

@Data
public class EditControlledUserDTO {
    private String name;
    private String email;
    private String password;
    private boolean active;
    private float salary;
    private int jobId;
    private List<EditControllerUseScheduleDTO> schedules;
}
