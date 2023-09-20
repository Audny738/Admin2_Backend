package com.gate_software.ams_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "days")
@Data
@NoArgsConstructor
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
