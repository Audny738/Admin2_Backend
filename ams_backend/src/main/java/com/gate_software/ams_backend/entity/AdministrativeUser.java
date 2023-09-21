package com.gate_software.ams_backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrative_users")
@Data
@NoArgsConstructor
public class AdministrativeUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String email;

    @Column
    private String password;

    public AdministrativeUser(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
