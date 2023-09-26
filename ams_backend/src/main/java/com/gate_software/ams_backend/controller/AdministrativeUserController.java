package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.dto.AdminUserDTO;
import com.gate_software.ams_backend.entity.AdministrativeUser;
import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.repository.AdministrativeUserRepository;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@Tag(name = "Administration")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class AdministrativeUserController {

    @Autowired
    private AdministrativeUserRepository administrativeUserRepository;

    @Autowired
    private ControlledUserRepository controlledUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/")
    @Operation(summary = "Create an Administrative User", description = "Create a new administrative user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"id\": 1, \"email\": \"john@example.com\"}"))),
            @ApiResponse(responseCode = "422", description = "Unprocessable Entity",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"message\": \"Email is already in use\"}")))
    })
    public ResponseEntity<?> createAdminUser(@RequestBody AdminUserDTO adminUserDTO) {

        String email = adminUserDTO.getEmail();
        AdministrativeUser existingAdminUser = administrativeUserRepository.findByEmail(email);
        ControlledUser existingControlledUser = controlledUserRepository.findByEmail(email);

        if (existingAdminUser != null || existingControlledUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Email is already in use");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(response);
        }

        AdministrativeUser adminUser = new AdministrativeUser();
        adminUser.setEmail(email);

        String encodedPassword = passwordEncoder.encode(adminUserDTO.getPassword());
        adminUser.setPassword(encodedPassword);

        AdministrativeUser savedAdminUser = administrativeUserRepository.save(adminUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdminUser);
    }

    @GetMapping("/")
    @Operation(summary = "Get All Administrative Users", description = "Get a list of all administrative users.")
    public ResponseEntity<List<AdministrativeUser>> getAllAdminUsers() {
        List<AdministrativeUser> adminUsers = administrativeUserRepository.findAll();
        return ResponseEntity.ok(adminUsers);
    }

    @GetMapping("/{adminUserId}")
    @Operation(summary = "Get an Administrative User by ID", description = "Get an administrative user by their ID.")
    @Parameters({
            @Parameter(name = "adminUserId", description = "ID of the administrative user to retrieve", required = true)
    })
    public ResponseEntity<AdministrativeUser> getAdminUserById(@PathVariable Integer adminUserId) {
        Optional<AdministrativeUser> adminUser = administrativeUserRepository.findById(adminUserId);
        if (adminUser.isPresent()) {
            return ResponseEntity.ok(adminUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{adminUserId}")
    @Operation(summary = "Update an Administrative User", description = "Update an existing administrative user.")
    @Parameters({
            @Parameter(name = "adminUserId", description = "ID of the administrative user to update", required = true)
    })
    public ResponseEntity<AdministrativeUser> updateAdminUser(@PathVariable Integer adminUserId, @RequestBody AdministrativeUser updatedAdminUser) {
        if (administrativeUserRepository.existsById(adminUserId)) {
            updatedAdminUser.setId(adminUserId);
            AdministrativeUser savedAdminUser = administrativeUserRepository.save(updatedAdminUser);
            return ResponseEntity.ok(savedAdminUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{adminUserId}")
    @Operation(summary = "Delete an Administrative User", description = "Delete an existing administrative user.")
    @Parameters({
            @Parameter(name = "adminUserId", description = "ID of the administrative user to delete", required = true)
    })
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Integer adminUserId) {
        if (administrativeUserRepository.existsById(adminUserId)) {
            administrativeUserRepository.deleteById(adminUserId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
