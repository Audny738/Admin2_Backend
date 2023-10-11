package com.gate_software.ams_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.gate_software.ams_backend.service.EmailService;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/test")
@Tag(name = "Hello controller")
@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST})
public class HelloController {
    @Autowired
    private EmailService emailService;

    @GetMapping
    @Operation(summary = "Hello world!")
    public ResponseEntity<String> helloWorld() {
        return new ResponseEntity<>("Hello world!", HttpStatus.OK);
    }

    private void testMails() {
        String email = "receptor-email@mail";

        Context na_context = new Context();
        na_context.setVariable("date", "11/09/2023");
        na_context.setVariable("time", "8:55 a.m.");
        na_context.setVariable("users", new String[]{
                "Antonio Linares Flores", "Rosa Martínez Ocampo", "Fatima Dieguez Llanos"
        });
        emailService.sendNonAttendanceEmail(email, na_context);
    }
}
