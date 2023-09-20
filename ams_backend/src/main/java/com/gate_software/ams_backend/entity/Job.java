package com.gate_software.ams_backend.entity;
import jakarta.persistence.*;
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
}
