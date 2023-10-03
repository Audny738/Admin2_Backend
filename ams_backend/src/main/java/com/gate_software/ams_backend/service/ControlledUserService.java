package com.gate_software.ams_backend.service;

import com.gate_software.ams_backend.dto.ControlledUserDTO;
import com.gate_software.ams_backend.dto.ControlledUserListDTO;
import com.gate_software.ams_backend.dto.JobDTO;
import com.gate_software.ams_backend.entity.*;
import com.gate_software.ams_backend.repository.AdministrativeUserRepository;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import com.gate_software.ams_backend.repository.JobRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ControlledUserService {

    @Autowired
    private AdministrativeUserRepository administrativeUserRepository;

    @Autowired
    private ControlledUserRepository controlledUserRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> createControlledUser(ControlledUserDTO userDTO){
        String email = userDTO.getEmail();
        AdministrativeUser existingAdminUser = administrativeUserRepository.findByEmail(email);
        ControlledUser existingControlledUser = controlledUserRepository.findByEmail(email);

        if (existingAdminUser != null || existingControlledUser != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Email is already in use");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(response);
        }

        Optional<Job> job = jobRepository.findById(userDTO.getJobId());

        if (job.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Job not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(response);
        }

        ControlledUser newUser = new ControlledUser();
        newUser.setName(userDTO.getName());
        newUser.setEmail(email);
        newUser.setJob(job.get());

        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
        newUser.setPassword(encodedPassword);

        newUser.setIsActive(userDTO.isActive());
        newUser.setSalary(userDTO.getSalary());

        ControlledUser savedUser = controlledUserRepository.save(newUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    public ResponseEntity<?> updateControlledUser(int userId, ControlledUserDTO userDTO) {
        Optional<ControlledUser> optionalUser = controlledUserRepository.findById(userId);

        if (optionalUser.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ControlledUser existingUser = optionalUser.get();

        String newEmail = userDTO.getEmail();
        if (!newEmail.equals(existingUser.getEmail())) {
            AdministrativeUser existingAdminUser = administrativeUserRepository.findByEmail(newEmail);
            ControlledUser existingControlledUser = controlledUserRepository.findByEmail(newEmail);

            if (existingAdminUser != null || existingControlledUser != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("message", "Email is already in use");
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
            }
        }

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(newEmail);

        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
            existingUser.setPassword(encodedPassword);
        }

        existingUser.setIsActive(userDTO.isActive());
        existingUser.setSalary(userDTO.getSalary());

        Optional<Job> job = jobRepository.findById(userDTO.getJobId());
        if (job.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Job not found.");
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
        }
        existingUser.setJob(job.get());

        ControlledUser updatedUser = controlledUserRepository.save(existingUser);

        return ResponseEntity.ok(updatedUser);
    }

    public List<CheckInRecords> getCheckInRecordsForLastMonth(int userId) {
        Optional<ControlledUser> optionalUser = controlledUserRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return null;
        }
        ControlledUser existingUser = optionalUser.get();

        LocalDateTime currentDate = LocalDateTime.now();
        int month = currentDate.getMonthValue();

        List<CheckInRecords> checkInRecordsLastMonth = existingUser.getCheckInRecords().stream()
                .filter(record -> {
                    LocalDateTime entryDatetime = record.getEntryDatetime().toLocalDateTime();
                    return entryDatetime.getMonthValue() == month;
                })
                .collect(Collectors.toList());

        return checkInRecordsLastMonth;
    }

    public List<CheckOutRecords> getCheckOutRecordsForLastMonth(int userId) {
        Optional<ControlledUser> optionalUser = controlledUserRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return null;
        }
        ControlledUser existingUser = optionalUser.get();

        LocalDateTime currentDate = LocalDateTime.now();
        int month = currentDate.getMonthValue();

        List<CheckOutRecords> checkOutRecordsLastMonth = existingUser.getCheckOutRecords().stream()
                .filter(record -> {
                    LocalDateTime exitDatetime = record.getExitDatetime().toLocalDateTime();
                    return exitDatetime.getMonthValue() == month;
                })
                .collect(Collectors.toList());

        return checkOutRecordsLastMonth;
    }

    public Page<ControlledUserListDTO> findActiveUsersDTOsPaginated(Pageable pageable) {
        Page<ControlledUser> userPage = controlledUserRepository.findByIsActiveTrue(pageable);
        return userPage.map(this::convertToDTO);
    }

    private ControlledUserListDTO convertToDTO(ControlledUser user) {
        ControlledUserListDTO userListDTO = new ControlledUserListDTO();
        List<CheckInRecords> checkInRecords = getCheckInRecordsForLastMonth(user.getId());
        boolean present = checkInRecords.stream()
                .anyMatch(record -> isToday(record.getEntryDatetime()));

        userListDTO.setId(user.getId());
        userListDTO.setName(user.getName());
        userListDTO.setEmail(user.getEmail());
        userListDTO.setSalary(user.getSalary());
        userListDTO.setPresent(present);
        int currentDayOfWeek = LocalDate.now().getDayOfWeek().getValue();
        List<Schedule> userSchedules = user.getSchedules();
        if (userSchedules != null && !userSchedules.isEmpty()) {
            List<String> todaySchedules = new ArrayList<>();
            for (Schedule schedule : userSchedules) {
                if (schedule.getEntryDay() != null && schedule.getEntryDay().getId() == currentDayOfWeek) {
                    String scheduleDescription = schedule.getEntryTime() + " - " + schedule.getExitTime();
                    todaySchedules.add(scheduleDescription);
                }
            }
            userListDTO.setSchedules(todaySchedules);
        }
        Job job = user.getJob();
        if (job != null) {
            String jobDescription = job.getName() + " (" + job.getArea() + ")";
            userListDTO.setJobDescription(jobDescription);
        }

        return userListDTO;
    }

    private boolean isToday(Timestamp entryDatetime) {
        LocalDate today = LocalDate.now();
        LocalDate recordDate = entryDatetime.toLocalDateTime().toLocalDate();
        return today.isEqual(recordDate);
    }
}
