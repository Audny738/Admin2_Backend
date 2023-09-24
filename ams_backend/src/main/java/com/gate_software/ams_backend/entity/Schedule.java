package com.gate_software.ams_backend.entity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne
    @JoinColumn(name = "controlled_user_id", nullable = false)
    @NotNull
    private ControlledUser controlledUser;
}
