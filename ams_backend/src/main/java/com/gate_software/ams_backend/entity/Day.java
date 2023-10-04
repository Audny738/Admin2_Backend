package com.gate_software.ams_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "days")
@Data
@NoArgsConstructor
public class Day {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", length = 10, nullable = false)
    private String name;

    public Day(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
