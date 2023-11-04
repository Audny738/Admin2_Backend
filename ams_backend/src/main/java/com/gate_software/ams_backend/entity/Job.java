package com.gate_software.ams_backend.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Table(name = "jobs")
@Data
@NoArgsConstructor
public class Job {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 50)
    @NotBlank(message = "The 'name' field is required")
    private String name;

    @Column(length = 50)
    @NotBlank(message = "The 'area' field is required")
    private String area;

    public Job(String name, String area) {
        this.name = name;
        this.area = area;
    }
}
