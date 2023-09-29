package com.gate_software.ams_backend.dto;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonFormat;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class AttendanceRecordDTO {
    private String dayName;
    @JsonFormat(pattern = "HH:mm - dd/MMM/yyyy", locale = "es")
    private LocalDateTime entryDatetime;
    @JsonFormat(pattern = "HH:mm - dd/MMM/yyyy", locale = "es")
    private LocalDateTime exitDatetime;

    public AttendanceRecordDTO(String dayName, LocalDateTime entryDatetime, LocalDateTime exitDatetime) {
        this.dayName = dayName;
        this.entryDatetime = entryDatetime;
        this.exitDatetime = exitDatetime;
    }
    public AttendanceRecordDTO() { }

    public void setExitDatetime(LocalDateTime exitDatetime) {
        this.exitDatetime = exitDatetime;
    }


}
