package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Tag(name = "Users")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class ControlledUserController {

    @Autowired
    private ControlledUserRepository controlledUserRepository;

    @PostMapping("")
    public ResponseEntity<ControlledUser> createUser(@RequestBody ControlledUser user) {
        ControlledUser savedUser = controlledUserRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("")
    public ResponseEntity<List<ControlledUser>> getAllUsers() {
        List<ControlledUser> users = controlledUserRepository.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ControlledUser> getUserById(@PathVariable Integer userId) {
        Optional<ControlledUser> user = controlledUserRepository.findById(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ControlledUser> updateUser(@PathVariable Integer userId, @RequestBody ControlledUser updatedUser) {
        if (controlledUserRepository.existsById(userId)) {
            updatedUser.setId(userId); // Asegura que el ID del usuario sea el mismo que el proporcionado en la URL
            ControlledUser savedUser = controlledUserRepository.save(updatedUser);
            return ResponseEntity.ok(savedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        if (controlledUserRepository.existsById(userId)) {
            controlledUserRepository.deleteById(userId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
