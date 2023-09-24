package com.gate_software.ams_backend.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "controlled_users")
@Data
@NoArgsConstructor
public class ControlledUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 50)
    private String name;

    @Column(nullable = false)
    @NotNull
    private String email;

    @Column(nullable = false)
    @NotNull
    private String password;

    @Column
    private boolean is_active;

    @Column
    private float salary;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @NotNull
    private Job job;

    @OneToMany(mappedBy = "controlledUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Schedule> schedules;
}
