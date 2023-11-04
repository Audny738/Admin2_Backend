package com.gate_software.ams_backend.service;
import com.gate_software.ams_backend.dto.CheckInOutRecordDTO;
import com.gate_software.ams_backend.entity.CheckInRecords;
import com.gate_software.ams_backend.entity.CheckOutRecords;
import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.repository.CheckOutRepository;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Log4j2
public class CheckOutService {
    @Autowired
    CheckOutRepository checkOutRepository;
    @Autowired
    private ControlledUserRepository controlledUserRepository;

    public ResponseEntity<?> createCheckOutRecord(CheckInOutRecordDTO checkInOutRecordDTO){
        Timestamp exitDatetime = null;
        Optional<ControlledUser> optionalControlledUser = controlledUserRepository.findById(checkInOutRecordDTO.getControlledUserId());
        if (optionalControlledUser.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Controlled User not found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        ControlledUser controlledUser = optionalControlledUser.get();
        CheckOutRecords newCheckOutRecord = new CheckOutRecords();
        newCheckOutRecord.setControlledUser(controlledUser);

        if(checkInOutRecordDTO.isTimestampSet()){
            exitDatetime = checkInOutRecordDTO.getDateTimeRecord();
        }

        newCheckOutRecord.setExitDatetime(exitDatetime);

        CheckOutRecords createdCheckOutRecord = checkOutRepository.save(newCheckOutRecord);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdCheckOutRecord);
    }

}
