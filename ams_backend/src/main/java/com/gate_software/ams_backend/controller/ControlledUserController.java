package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    @PostMapping("")
    @Operation(summary = "Create a Controlled User", description = "Create a new controlled user.")
    public ResponseEntity<ControlledUser> createUser(@RequestBody ControlledUser user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        ControlledUser savedUser = controlledUserRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("")
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
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update a Controlled User", description = "Update an existing controlled user.")
    @Parameters({
            @Parameter(name = "userId", description = "ID of the controlled user to update", required = true)
    })
    public ResponseEntity<ControlledUser> updateUser(@PathVariable Integer userId, @RequestBody ControlledUser updatedUser) {
        if (controlledUserRepository.existsById(userId)) {
            updatedUser.setId(userId); // Ensure that the user ID is the same as provided in the URL
            ControlledUser savedUser = controlledUserRepository.save(updatedUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
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
}
