package com.gate_software.ams_backend.controller;

import com.gate_software.ams_backend.config.DataSeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/seed")
public class SeedController {
    @Autowired
    private DataSeed dataSeed;

    @GetMapping
    public String seedDatabase() {
        dataSeed.loadData();
        return "Data seeded successfully!";
    }
}
