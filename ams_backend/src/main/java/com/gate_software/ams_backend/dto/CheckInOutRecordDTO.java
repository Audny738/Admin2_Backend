package com.gate_software.ams_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gate_software.ams_backend.entity.ControlledUser;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

public class CheckInOutRecordDTO {
    private boolean arriving;
    @JsonIgnore
    private int controlledUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    private Timestamp dateTimeRecord;

    public boolean isArriving() { return arriving; }

    public int getControlledUserId() {
        return controlledUserId;
    }

    public void setControlledUserId(int controlledUserId) { this.controlledUserId = controlledUserId; }

    public Timestamp getDateTimeRecord() { return dateTimeRecord; }

    @JsonProperty("dateTimeRecord")
    @JsonIgnore
    public boolean isTimestampSet() {
        return dateTimeRecord != null && StringUtils.isNotBlank(dateTimeRecord.toString());
    }
}
