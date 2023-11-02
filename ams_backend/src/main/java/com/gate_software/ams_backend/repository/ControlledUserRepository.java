package com.gate_software.ams_backend.repository;

import com.gate_software.ams_backend.entity.CheckInRecords;
import com.gate_software.ams_backend.entity.ControlledUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ControlledUserRepository extends JpaRepository<ControlledUser, Integer> {

    ControlledUser findByEmail(String email);

    Page<ControlledUser> findByIsActiveTrue(Pageable pageable);

    List<ControlledUser> findAllByIsActiveTrue();

    boolean existsByJobId(Integer job_id);
}
