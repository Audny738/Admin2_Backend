package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.dto.AuthenticatedUserDTO;
import com.gate_software.ams_backend.dto.LoginRequestDTO;
import com.gate_software.ams_backend.service.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @PostMapping("/")
    @Operation(summary = "Authenticate user", description = "Authenticate a user based on email and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"user\": {\"id\": 1, \"typeUser\": \"administrative/controlled\"}, \"status\": true}"))),
            @ApiResponse(responseCode = "401", description = "Authentication failed",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"user\": null, \"status\": false}")))
    })
    public Map<String, Object> login(@RequestBody LoginRequestDTO requestDTO) {

        AuthenticatedUserDTO user = loginService.authenticate(requestDTO.getEmail(), requestDTO.getPassword());

        Map<String, Object> response = new HashMap<>();

        response.put("user", user);

        if (user != null) {
            response.put("status", true);
        } else {
            response.put("status", false);
        }
        return response;
    }
}
