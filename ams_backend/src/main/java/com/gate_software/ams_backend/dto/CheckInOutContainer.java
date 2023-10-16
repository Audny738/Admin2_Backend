package com.gate_software.ams_backend.dto;

import com.gate_software.ams_backend.entity.CheckInRecords;
import com.gate_software.ams_backend.entity.CheckOutRecords;

public class CheckInOutContainer {
    private CheckInRecords checkInRecord;
    private CheckOutRecords checkOutRecord;

    public CheckInOutContainer(CheckInRecords checkInRecord, CheckOutRecords checkOutRecord) {
        this.checkInRecord = checkInRecord;
        this.checkOutRecord = checkOutRecord;
    }

    public CheckInRecords getCheckInRecord() {
        return checkInRecord;
    }

    public CheckOutRecords getCheckOutRecord() {
        return checkOutRecord;
    }
}
