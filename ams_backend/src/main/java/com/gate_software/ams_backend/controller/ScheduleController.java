package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.entity.Schedule;
import com.gate_software.ams_backend.repository.ScheduleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/schedules")
@Tag(name = "Schedules")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class ScheduleController {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostMapping("/")
    @Operation(summary = "Create a new Schedule")
    public ResponseEntity<Schedule> createSchedule(@RequestBody Schedule schedule) {
        Schedule createdSchedule = scheduleRepository.save(schedule);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSchedule);
    }

    @GetMapping("/")
    @Operation(summary = "Get all Schedules")
    public ResponseEntity<List<Schedule>> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return ResponseEntity.ok(schedules);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a Schedule by ID")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable int id) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(id);
        return scheduleOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a Schedule by ID")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable int id, @RequestBody Schedule updatedSchedule) {
        if (!scheduleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedSchedule.setId(id);
        Schedule savedSchedule = scheduleRepository.save(updatedSchedule);
        return ResponseEntity.ok(savedSchedule);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Schedule by ID")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id) {
        if (!scheduleRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        scheduleRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
