package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.dto.CheckInOutRecordDTO;
import com.gate_software.ams_backend.dto.ControlledUserDTO;
import com.gate_software.ams_backend.entity.*;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import com.gate_software.ams_backend.service.AttendanceHistoryService;
import com.gate_software.ams_backend.service.CheckInService;
import com.gate_software.ams_backend.service.CheckOutService;
import com.gate_software.ams_backend.service.ControlledUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Tag(name = "Users")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class ControlledUserController {

    @Autowired
    private ControlledUserRepository controlledUserRepository;

    @Autowired
    private ControlledUserService controlledUserService;

    @Autowired
    private CheckInService checkInService;
    @Autowired
    private CheckOutService checkOutService;
    @Autowired
    private AttendanceHistoryService attendanceHistoryService;

    @PostMapping("/")
    @Operation(summary = "Create a Controlled User", description = "Create a new controlled user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"id\": 1, \"name\": \"John Doe\", \"email\": \"john@example.com\", \"isActive\": true, \"salary\": 50000.0, \"job\": {\"id\": 1, \"name\": \"Job Name\"}}"))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Email is already in use\"}")))
    })
    public ResponseEntity<?> createControlledUser(@RequestBody ControlledUserDTO userDTO) throws ChangeSetPersister.NotFoundException {
        ResponseEntity<?> createdUser = controlledUserService.createControlledUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/")
    @Operation(summary = "Get All Controlled Users", description = "Get a list of all controlled users.")
    public ResponseEntity<List<ControlledUser>> getAllUsers() {
        List<ControlledUser> users = controlledUserRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get a Controlled User by ID", description = "Get a controlled user by their ID.")
    @Parameters({
            @Parameter(name = "userId", description = "ID of the controlled user to retrieve", required = true)
    })
    public ResponseEntity<ControlledUser> getUserById(@PathVariable Integer userId) {
        Optional<ControlledUser> user = controlledUserRepository.findById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update a Controlled User", description = "Update an existing controlled user.")
    @Parameters({
            @Parameter(name = "userId", description = "ID of the controlled user to update", required = true)
    })
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody ControlledUserDTO userDTO) {
        ResponseEntity<?> createdUser = controlledUserService.updateControlledUser(userId, userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a Controlled User", description = "Delete an existing controlled user.")
    @Parameters({
            @Parameter(name = "userId", description = "ID of the controlled user to delete", required = true)
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        if (controlledUserRepository.existsById(userId)) {
            controlledUserRepository.deleteById(userId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{userId}/schedules")
    @Operation(summary = "Get Schedules for a Controlled User", description = "Get all schedules associated with a controlled user.")
    @Parameters({
            @Parameter(name = "userId", description = "ID of the controlled user to retrieve schedules for", required = true)
    })
    public ResponseEntity<Object> getSchedulesForUser(@PathVariable int userId) {
        Optional<ControlledUser> userOptional = controlledUserRepository.findById(userId);

        if (userOptional.isPresent()) {
            ControlledUser user = userOptional.get();
            List<Schedule> schedules = user.getSchedules();
            return ResponseEntity.ok(schedules);
        } else {
            return ResponseEntity.unprocessableEntity()
                    .body("Controlled user not found with ID: " + userId);
        }
    }

    //attendance_history
    @GetMapping("/{userId}/attendance_history")
    @Operation(summary = "Get the attendance history for a Controlled User", description = "Get all checkin and checkout recors associated with a controlled user.")
    @Parameters({
            @Parameter(name = "userId", description = "ID of the controlled user to retrieve checkin and checkout records", required = true)
    })
    public ResponseEntity<Object> getAttendanceHistoryForUser(@PathVariable int userId, @RequestParam(required = false, defaultValue = "10") int limit) {
        Optional<ControlledUser> userOptional = controlledUserRepository.findById(userId);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok().body(attendanceHistoryService.getAttendanceHistory(userId, limit));
        } else {
            return ResponseEntity.unprocessableEntity()
                    .body("Controlled user not found with ID: " + userId);
        }
    }

    @PostMapping("/user/{userId}/register_inout")
    @Operation(summary = "Create a new record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Record created successfully",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<?> createCheckInOutRegister(@RequestBody CheckInOutRecordDTO checkInOutRecordDTO, @PathVariable int userId) {
        Optional<ControlledUser> userOptional = controlledUserRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.unprocessableEntity()
                    .body("Controlled user not found with ID: " + userId);
        }
        checkInOutRecordDTO.setControlledUserId(userId);
        boolean isArriving = checkInOutRecordDTO.isArriving();
        return isArriving ? checkInService.createCheckInRecord(checkInOutRecordDTO) : checkOutService.createCheckOutRecord(checkInOutRecordDTO);
    }
}
