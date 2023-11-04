package com.gate_software.ams_backend.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WorkHourRecordDTO {
    private String userName;
    private LocalDate date;
    private long hoursWorked;
    private float salary;
    private float totalPayment;

    public WorkHourRecordDTO(String userName, LocalDate date, long hoursWorked, float salary) {
        this.userName = userName;
        this.date = date;
        this.hoursWorked = hoursWorked;
        this.salary = salary;
        this.totalPayment = hoursWorked * salary;
    }
}
