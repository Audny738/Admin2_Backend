package com.gate_software.ams_backend.repository;

import com.gate_software.ams_backend.entity.AdministrativeUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrativeUserRepository extends JpaRepository<AdministrativeUser, Integer> {
}
