package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.dto.ScheduleDTO;
import com.gate_software.ams_backend.entity.Schedule;
import com.gate_software.ams_backend.error.AMSException;
import com.gate_software.ams_backend.repository.ScheduleRepository;
import com.gate_software.ams_backend.service.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
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
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @PostMapping("/")
    @Operation(summary = "Create a new Schedule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Schedule created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Schedule.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AMSException.class)))
    })
    public ResponseEntity<?> createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.createSchedule(scheduleDTO);
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
    public ResponseEntity<?> updateSchedule(@PathVariable int id, @RequestBody ScheduleDTO scheduleDTO) {
        return scheduleService.updateSchedule(id, scheduleDTO);
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
