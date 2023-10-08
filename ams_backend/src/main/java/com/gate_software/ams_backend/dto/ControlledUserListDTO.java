package com.gate_software.ams_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import java.util.List;

@Data
public class ControlledUserListDTO {
    private Integer id;
    private String name;
    private String email;
    private float salary;
    @JsonIgnore
    private JobDTO job;
    private String jobDescription;
    private boolean present;
    private List<String> schedules;
}
