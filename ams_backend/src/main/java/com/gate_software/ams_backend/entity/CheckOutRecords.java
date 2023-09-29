package com.gate_software.ams_backend.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

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
    private Timestamp exitDatetime;

    @ManyToOne
    @JoinColumn(name = "controlled_user_id")
    @JsonIgnore
    private ControlledUser controlledUser;

    public void setExitDatetime(){ this.exitDatetime = new Timestamp(new Date().getTime()); }

    public int getDateNumber(){
        return this.exitDatetime.toLocalDateTime().getDayOfWeek().getValue();
    }
}
