package com.gate_software.ams_backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gate_software.ams_backend.entity.ControlledUser;

public class CheckInOutRecordDTO {
    private boolean arriving;
    @JsonIgnore
    private int controlledUserId;

    public boolean isArriving() { return arriving; }

    public int getControlledUserId() {
        return controlledUserId;
    }

    public void setControlledUserId(int controlledUserId) { this.controlledUserId = controlledUserId; }
}
