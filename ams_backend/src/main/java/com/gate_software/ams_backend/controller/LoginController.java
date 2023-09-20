package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.service.LoginService;
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
    public Map<String, Object> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");

        boolean isAuthenticated = loginService.authenticate(email, password);

        Map<String, Object> response = new HashMap<>();
        response.put("status", isAuthenticated);
        return response;
    }
}
