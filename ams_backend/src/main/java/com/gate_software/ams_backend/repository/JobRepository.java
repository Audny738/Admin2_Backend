package com.gate_software.ams_backend.repository;

import com.gate_software.ams_backend.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
