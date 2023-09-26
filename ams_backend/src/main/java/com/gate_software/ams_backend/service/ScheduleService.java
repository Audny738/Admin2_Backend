package com.gate_software.ams_backend.service;
import com.gate_software.ams_backend.dto.ScheduleDTO;
import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.entity.Day;
import com.gate_software.ams_backend.entity.Schedule;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import com.gate_software.ams_backend.repository.DayRepository;
import com.gate_software.ams_backend.repository.ScheduleRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class ScheduleService {

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private ControlledUserRepository controlledUserRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<?> createSchedule(ScheduleDTO scheduleDTO) {
        String timeRegex = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
        if (!scheduleDTO.getEntryTime().matches(timeRegex) || !scheduleDTO.getExitTime().matches(timeRegex)) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid time format. Use HH:mm:ss.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<Day> optionalEntryDay = dayRepository.findById(scheduleDTO.getEntryDayId());

        if (optionalEntryDay.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Entry Day not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Day entryDay = optionalEntryDay.get();

        Optional<Day> optionalExitDay = dayRepository.findById(scheduleDTO.getExitDayId());
        if (optionalExitDay.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Exit Day not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Day exitDay = optionalExitDay.get();

        Optional<ControlledUser> optionalControlledUser = controlledUserRepository.findById(scheduleDTO.getControlledUserId());
        if (optionalControlledUser.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Controlled User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ControlledUser controlledUser = optionalControlledUser.get();

        Schedule newSchedule = new Schedule();
        newSchedule.setEntryDay(entryDay);
        newSchedule.setEntryTime(scheduleDTO.getEntryTime());
        newSchedule.setExitDay(exitDay);
        newSchedule.setExitTime(scheduleDTO.getExitTime());
        newSchedule.setControlledUser(controlledUser);

        Schedule createdSchedule = scheduleRepository.save(newSchedule);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    public ResponseEntity<?> updateSchedule(int id, ScheduleDTO scheduleDTO) {
        String timeRegex = "^([01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d$";
        if (!scheduleDTO.getEntryTime().matches(timeRegex) || !scheduleDTO.getExitTime().matches(timeRegex)) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Invalid time format. Use HH:mm:ss.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Optional<Day> optionalEntryDay = dayRepository.findById(scheduleDTO.getEntryDayId());

        if (optionalEntryDay.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Entry Day not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Day entryDay = optionalEntryDay.get();

        Optional<Day> optionalExitDay = dayRepository.findById(scheduleDTO.getExitDayId());
        if (optionalExitDay.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Exit Day not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        Day exitDay = optionalExitDay.get();

        Optional<ControlledUser> optionalControlledUser = controlledUserRepository.findById(scheduleDTO.getControlledUserId());
        if (optionalControlledUser.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Controlled User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ControlledUser controlledUser = optionalControlledUser.get();

        Schedule updatedSchedule = new Schedule();
        updatedSchedule.setId(id);
        updatedSchedule.setEntryDay(entryDay);
        updatedSchedule.setEntryTime(scheduleDTO.getEntryTime());
        updatedSchedule.setExitDay(exitDay);
        updatedSchedule.setExitTime(scheduleDTO.getExitTime());
        updatedSchedule.setControlledUser(controlledUser);

        Schedule savedSchedule = scheduleRepository.save(updatedSchedule);

        return ResponseEntity.ok(savedSchedule);
    }
}
