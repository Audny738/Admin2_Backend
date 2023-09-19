package com.gate_software.ams_backend.repository;

import com.gate_software.ams_backend.entity.CheckInRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckInRepository extends JpaRepository<CheckInRecords, Long> {
}
