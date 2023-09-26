package com.gate_software.ams_backend.dto;

public class ControlledUserDTO {
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private float salary;
    private int jobId;

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public float getSalary() {
        return this.salary;
    }

    public int getJobId() {
        return this.jobId;
    }
}
