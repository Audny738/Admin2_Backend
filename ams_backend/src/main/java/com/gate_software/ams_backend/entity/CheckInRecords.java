package com.gate_software.ams_backend.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "check_in_records")
@Data
@NoArgsConstructor
public class CheckInRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
}
