package com.gate_software.ams_backend.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private String password;

    @Column(name = "is_active")
    private boolean isActive;

    @Column
    private float salary;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    @NotNull
    private Job job;

    @OneToMany(mappedBy = "controlledUser")
    @JsonIgnore
    private List<Schedule> schedules;

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
