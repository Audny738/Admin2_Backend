package com.gate_software.ams_backend.config;

import com.gate_software.ams_backend.entity.Day;
import com.gate_software.ams_backend.repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DaysSeed implements CommandLineRunner {
    @Autowired
    private DayRepository dayRepository;

    @Override
    public void run(String... args) throws Exception {
        if (dayRepository.count() == 0) {
            dayRepository.save(new Day(1, "Lunes"));
            dayRepository.save(new Day(2, "Martes"));
            dayRepository.save(new Day(3, "Miércoles"));
            dayRepository.save(new Day(4, "Jueves"));
            dayRepository.save(new Day(5, "Viernes"));
            dayRepository.save(new Day(6, "Sábado"));
            dayRepository.save(new Day(7, "Domingo"));
        }
    }
}
