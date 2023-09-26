package com.gate_software.ams_backend.dto;

public class ScheduleDTO {
    private int entryDayId;
    private String entryTime;
    private int exitDayId;
    private String exitTime;
    private int controlledUserId;

    public int getEntryDayId() {
        return entryDayId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public int getExitDayId() {
        return exitDayId;
    }

    public String getExitTime() {
        return exitTime;
    }

    public int getControlledUserId() {
        return controlledUserId;
    }
}
