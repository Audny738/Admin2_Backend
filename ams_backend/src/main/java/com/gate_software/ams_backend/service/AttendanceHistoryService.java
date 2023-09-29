package com.gate_software.ams_backend.service;

import com.gate_software.ams_backend.dto.AttendanceRecordDTO;
import com.gate_software.ams_backend.entity.CheckInRecords;
import com.gate_software.ams_backend.entity.CheckOutRecords;
import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.entity.Day;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import com.gate_software.ams_backend.repository.DayRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AttendanceHistoryService {
    @Autowired
    private ControlledUserService controlledUserService;
    @Autowired
    private DayRepository dayRepository;
    public ResponseEntity<?> getAttendanceHistory(int userId, int limit){
        List<CheckInRecords> checkInRecords = controlledUserService.getCheckInRecordsForLastMonth(userId);
        List<CheckOutRecords> checkOutRecords = controlledUserService.getCheckOutRecordsForLastMonth(userId);

        Map<Integer, List<AttendanceRecordDTO>> attendanceMap = new HashMap<>();

        for (CheckInRecords checkInRecord : checkInRecords) {
            Optional<Day> day = dayRepository.findById(checkInRecord.getDateNumber());
            attendanceMap.computeIfAbsent(checkInRecord.getEntryDatetime().toLocalDateTime().getDayOfMonth(), k -> new ArrayList<>())
                    .add(new AttendanceRecordDTO(
                            day.get().getName(),
                            checkInRecord.getEntryDatetime().toLocalDateTime(),
                            null
                    ));
        }

        for (CheckOutRecords checkOutRecord : checkOutRecords) {
            Optional<Day> day = dayRepository.findById(checkOutRecord.getDateNumber());
            int dayOfMonth = checkOutRecord.getExitDatetime().toLocalDateTime().getDayOfMonth();
            List<AttendanceRecordDTO> attendanceRecordsTotal = attendanceMap.computeIfAbsent(dayOfMonth, k -> new ArrayList<>());
            if (attendanceRecordsTotal.isEmpty()) {
                attendanceRecordsTotal.add(new AttendanceRecordDTO(
                        day.get().getName(),
                        null,
                        checkOutRecord.getExitDatetime().toLocalDateTime()
                ));
            } else {
                attendanceRecordsTotal.forEach(record -> record.setExitDatetime(checkOutRecord.getExitDatetime().toLocalDateTime()));
            }
        }

        List<AttendanceRecordDTO> attendanceList = attendanceMap.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        if (limit > 0 && attendanceList.size() > limit) {
            attendanceList = attendanceList.subList(0, limit);
        }

        return ResponseEntity.ok(attendanceList);
    }
}
