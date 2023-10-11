package com.gate_software.ams_backend.repository;


import com.gate_software.ams_backend.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {
}
