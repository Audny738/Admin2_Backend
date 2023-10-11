package com.gate_software.ams_backend.service;
import com.gate_software.ams_backend.dto.CheckInOutRecordDTO;
import com.gate_software.ams_backend.entity.CheckInRecords;
import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.repository.CheckInRepository;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class CheckInService {
    @Autowired
    private EmailService emailService;
    @Autowired
    CheckInRepository checkInRepository;
    @Autowired
    private ControlledUserRepository controlledUserRepository;

    public ResponseEntity<?> createCheckInRecord(CheckInOutRecordDTO checkInOutRecordDTO){
        Timestamp entryDatetime = null;
        Optional<ControlledUser> optionalControlledUser = controlledUserRepository.findById(checkInOutRecordDTO.getControlledUserId());
        if (optionalControlledUser.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Controlled User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ControlledUser controlledUser = optionalControlledUser.get();
        CheckInRecords newCheckInRecord = new CheckInRecords();
        newCheckInRecord.setControlledUser(controlledUser);

        if(checkInOutRecordDTO.isTimestampSet()){
            entryDatetime = checkInOutRecordDTO.getDateTimeRecord();
        }
        newCheckInRecord.setEntryDatetime(entryDatetime);

        CheckInRecords createdCheckInRecord = checkInRepository.save(newCheckInRecord);

        notifyCheckIn(controlledUser.getEmail(), controlledUser.getName(), newCheckInRecord.getEntryDatetime());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCheckInRecord);
    }

    private void notifyCheckIn(String email, String name, Timestamp datetime) {
        Context emailData = new Context();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("KK:mm a");

        emailData.setVariable("name", name);
        emailData.setVariable("date", datetime.toLocalDateTime().format(dateFormatter));
        emailData.setVariable("time", datetime.toLocalDateTime().format(timeFormatter));

        emailService.sendAttendanceEmail(email, emailData);
    }
}
