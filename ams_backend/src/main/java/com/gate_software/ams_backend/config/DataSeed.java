package com.gate_software.ams_backend.config;

import com.gate_software.ams_backend.entity.*;
import com.gate_software.ams_backend.repository.*;
import com.gate_software.ams_backend.service.CheckOutService;
import com.gate_software.ams_backend.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class DataSeed {
    @Autowired
    private AdministrativeUserRepository administrativeUserRepository;
    @Autowired
    private ControlledUserRepository controlledUserRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private CheckInRepository checkInRepository;
    @Autowired
    private CheckOutRepository checkOutRepository;
    @Autowired
    private DayRepository dayRepository;
    public void loadData() {
        AdministrativeUser adminUser = new AdministrativeUser("joshua@gmail.com", "admin1234");
        Job job = new Job("Tecnología", "Ingeniero de Software");
        ControlledUser user = new ControlledUser("Rodrigo Urtecho","rodrigo@gmail.com", "1234", true, 10000, job);
        Schedule schedule = new Schedule(dayRepository.getReferenceById(1), "08:00:00", dayRepository.getReferenceById(1), "12:00:00", user);
        Schedule schedule1 = new Schedule(dayRepository.getReferenceById(2), "08:00:00", dayRepository.getReferenceById(2), "12:00:00", user);
        LocalDateTime dateTime = LocalDateTime.of(2023, 10, 1, 8, 0);
        Timestamp dateTimeRecord = Timestamp.valueOf(dateTime);
        CheckInRecords checkInRecord = new CheckInRecords(dateTimeRecord, user);
        dateTime = LocalDateTime.of(2023, 10, 1, 12, 0);
        dateTimeRecord = Timestamp.valueOf(dateTime);
        CheckOutRecords checkOutRecord = new CheckOutRecords(dateTimeRecord, user);

        administrativeUserRepository.save(adminUser);
        jobRepository.save(job);
        controlledUserRepository.save(user);
        scheduleRepository.save(schedule);
        scheduleRepository.save(schedule1);
        checkInRepository.save(checkInRecord);
        checkOutRepository.save(checkOutRecord);

        dateTime = LocalDateTime.of(2023, 10, 2, 8, 0);
        dateTimeRecord = Timestamp.valueOf(dateTime);
        checkInRepository.save(new CheckInRecords(dateTimeRecord, user));
        dateTime = LocalDateTime.of(2023, 10, 2, 12, 0);
        dateTimeRecord = Timestamp.valueOf(dateTime);
        checkOutRepository.save(new CheckOutRecords(dateTimeRecord, user));
    }
}
