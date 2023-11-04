package com.gate_software.ams_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gate_software.ams_backend.entity.ControlledUser;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;

@Data
public class CheckInOutRecordDTO {
    private boolean arriving;
    @JsonIgnore
    private int controlledUserId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "America/Mexico_City")
    private Timestamp dateTimeRecord;

    public boolean isArriving() { return arriving; }

    @JsonProperty("dateTimeRecord")
    @JsonIgnore
    public boolean isTimestampSet() {
        return dateTimeRecord != null && StringUtils.isNotBlank(dateTimeRecord.toString());
    }
}
