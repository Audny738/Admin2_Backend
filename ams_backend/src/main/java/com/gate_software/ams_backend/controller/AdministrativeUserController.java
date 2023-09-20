package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.entity.AdministrativeUser;
import com.gate_software.ams_backend.repository.AdministrativeUserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@Tag(name = "Administration")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class AdministrativeUserController {

    @Autowired
    private AdministrativeUserRepository administrativeUserRepository;

    @PostMapping("")
    public ResponseEntity<AdministrativeUser> createAdminUser(@RequestBody AdministrativeUser adminUser) {
        AdministrativeUser savedAdminUser = administrativeUserRepository.save(adminUser);
        return ResponseEntity.ok(savedAdminUser);
    }

    @GetMapping("")
    public ResponseEntity<List<AdministrativeUser>> getAllAdminUsers() {
        List<AdministrativeUser> adminUsers = administrativeUserRepository.findAll();
        return ResponseEntity.ok(adminUsers);
    }

    @GetMapping("/{adminUserId}")
    public ResponseEntity<AdministrativeUser> getAdminUserById(@PathVariable Integer adminUserId) {
        Optional<AdministrativeUser> adminUser = administrativeUserRepository.findById(adminUserId);
        if (adminUser.isPresent()) {
            return ResponseEntity.ok(adminUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{adminUserId}")
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
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Integer adminUserId) {
        if (administrativeUserRepository.existsById(adminUserId)) {
            administrativeUserRepository.deleteById(adminUserId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
