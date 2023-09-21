package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
@Tag(name = "Login")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping
    @Operation(summary = "Authenticate user", description = "Authenticate a user based on email and password.")
    public Map<String, Object> login(
            @Parameter(description = "User's email") @RequestParam String email,
            @Parameter(description = "User's password") @RequestParam String password) {

        boolean isAuthenticated = loginService.authenticate(email, password);

        Map<String, Object> response = new HashMap<>();
        response.put("status", isAuthenticated);
        return response;
    }
}
