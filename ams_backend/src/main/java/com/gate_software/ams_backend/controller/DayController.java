package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.entity.Day;
import com.gate_software.ams_backend.repository.DayRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/days")
@Tag(name = "Days")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class DayController {

    @Autowired
    private DayRepository dayRepository;

    @PostMapping("/")
    @Operation(summary = "Create a Day", description = "Create a new day.")
    public ResponseEntity<Day> createDay(@RequestBody Day day) {
        Day createdDay = dayRepository.save(day);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDay);
    }

    @GetMapping("/")
    @Operation(summary = "Get All Days", description = "Get a list of all days.")
    public ResponseEntity<List<Day>> getAllDays() {
        List<Day> days = dayRepository.findAll();
        return ResponseEntity.ok(days);
    }

    @GetMapping("/{dayId}")
    @Operation(summary = "Get a Day by ID", description = "Get a day by its ID.")
    @Parameters({
            @Parameter(name = "dayId", description = "ID of the day to retrieve", required = true)
    })
    public ResponseEntity<Day> getDayById(@PathVariable Integer dayId) {
        Optional<Day> dayOptional = dayRepository.findById(dayId);
        return dayOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{dayId}")
    @Operation(summary = "Update a Day", description = "Update an existing day.")
    @Parameters({
            @Parameter(name = "dayId", description = "ID of the day to update", required = true)
    })
    public ResponseEntity<Day> updateDay(@PathVariable Integer dayId, @RequestBody Day updatedDay) {
        if (dayRepository.existsById(dayId)) {
            updatedDay.setId(dayId);
            Day savedDay = dayRepository.save(updatedDay);
            return ResponseEntity.ok(savedDay);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{dayId}")
    @Operation(summary = "Delete a Day", description = "Delete an existing day.")
    @Parameters({
            @Parameter(name = "dayId", description = "ID of the day to delete", required = true)
    })
    public ResponseEntity<Void> deleteDay(@PathVariable Integer dayId) {
        if (dayRepository.existsById(dayId)) {
            dayRepository.deleteById(dayId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
