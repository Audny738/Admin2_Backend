package com.gate_software.ams_backend.dto;

import lombok.Data;

@Data
public class ScheduleDTO {
    private int entryDayId;
    private String entryTime;
    private int exitDayId;
    private String exitTime;
    private int controlledUserId;
}
