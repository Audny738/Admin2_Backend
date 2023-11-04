package com.gate_software.ams_backend.config;

import com.gate_software.ams_backend.entity.AdministrativeUser;
import com.gate_software.ams_backend.entity.ControlledUser;
import com.gate_software.ams_backend.repository.AdministrativeUserRepository;
import com.gate_software.ams_backend.repository.ControlledUserRepository;
import com.gate_software.ams_backend.service.ControlledUserService;
import com.gate_software.ams_backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class NonAttendanceNotificator {
    @Autowired
    private AdministrativeUserRepository administrativeUserRepository;
    @Autowired
    private ControlledUserRepository controlledUserRepository;
    @Autowired
    private ControlledUserService controlledUserService;
    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 12 ? * *")
    public void checkDailyAssistance() {
        List<String> namesList = controlledUserRepository.findAll()
                .stream()
                .filter(ControlledUser::isActive)
                .filter(controlledUser -> !controlledUserService.verifyIsPresent(controlledUser.getId()))
                .map(ControlledUser::getName)
                .toList();

        if (!namesList.isEmpty()) {
            notifyNonAttendance(namesList, LocalDateTime.now());
        }
    }

    private void notifyNonAttendance(Iterable<String> users, LocalDateTime dateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("KK:mm a");

        for (AdministrativeUser admin : administrativeUserRepository.findAll()) {
            Context emailData = new Context();

            emailData.setVariable("date", dateTime.format(dateFormatter));
            emailData.setVariable("time", dateTime.format(timeFormatter));
            emailData.setVariable("users", users);

            emailService.sendNonAttendanceEmail(admin.getEmail(), emailData);
        }
    }
}
