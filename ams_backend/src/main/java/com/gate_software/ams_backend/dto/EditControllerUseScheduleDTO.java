package com.gate_software.ams_backend.dto;

import lombok.Data;

@Data
public class EditControllerUseScheduleDTO {
    private int id;
    private int entryDayId;
    private String entryTime;
    private int exitDayId;
    private String exitTime;
}
