package com.gate_software.ams_backend.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "controlled_users")
@Data
@NoArgsConstructor
public class ControlledUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(length = 30)
    private String email;

    @Column(length = 30)
    private String password;

    public ControlledUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
