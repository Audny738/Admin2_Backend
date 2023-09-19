package com.gate_software.ams_backend.repository;

import com.gate_software.ams_backend.entity.CheckOutRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckOutRepository extends JpaRepository<CheckOutRecords, Long> {
}
