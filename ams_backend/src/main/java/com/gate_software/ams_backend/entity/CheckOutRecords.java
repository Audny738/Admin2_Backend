package com.gate_software.ams_backend.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "check_out_records")
@Data
@NoArgsConstructor
public class CheckOutRecords {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "exit_datetime", updatable = false)
    @JsonIgnore
    private Timestamp exitDatetime;

    @Column(name = "controlled_user_id ")
    private int controlledUserId;
}
