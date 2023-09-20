package com.gate_software.ams_backend.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
public class Schedule {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "entry_day_id", referencedColumnName = "id")
    private Day entryDay;

    @Column(name = "entry_time")
    private Time entry_time;

    @ManyToOne
    @JoinColumn(name = "exit_day_id", referencedColumnName = "id")
    private Day exitDay;

    @Column(name = "exit_time")
    private Time exit_time;

    @Column(name = "controlled_user_id ")
    private int controlledUserId;
}
