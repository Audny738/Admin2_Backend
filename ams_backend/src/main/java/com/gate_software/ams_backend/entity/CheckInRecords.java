package com.gate_software.ams_backend.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "check_in_records")
@Data
@NoArgsConstructor
public class CheckInRecords {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "entry_datetime", updatable = false)
    private Timestamp entryDatetime;

    @ManyToOne
    @JoinColumn(name = "controlled_user_id")
    @JsonIgnore
    private ControlledUser controlledUser;

    public void setEntryDatetime(Timestamp entryDatetime) {
        if (entryDatetime != null) {
            this.entryDatetime = entryDatetime;
        } else {
            this.entryDatetime = new Timestamp(new Date().getTime());
        }
    }

    public int getDateNumber(){
        return this.entryDatetime.toLocalDateTime().getDayOfWeek().getValue();
    }

    public CheckInRecords(Timestamp entryDatetime, ControlledUser controlledUser) {
        this.entryDatetime = entryDatetime;
        this.controlledUser = controlledUser;
    }
}
