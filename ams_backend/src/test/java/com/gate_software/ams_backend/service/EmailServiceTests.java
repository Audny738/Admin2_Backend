package com.gate_software.ams_backend.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mockito.Mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTests {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("KK:mm a");
    private static final LocalDateTime DATE_TIME = LocalDateTime.of(2023, 10, 20, 12, 44, 30);
    private static final String NAME = "Jhon Doe";
    private static final String EMAIL = "gatesoftware.ams@gmail.com";
    @InjectMocks
    private EmailService emailService;
    @Mock
    private TemplateEngine templateEngine;

    @Test
    public void EmailService_SendAttendanceEmail_ReturnsTrue() {
        Context emailData = new Context();
        emailData.setVariable("name", NAME);
        emailData.setVariable("date", DATE_TIME.format(DATE_FORMATTER));
        emailData.setVariable("time", DATE_TIME.format(TIME_FORMATTER));

        when(templateEngine.process("attendance_email", emailData)).thenReturn("Email body content");

        Assertions.assertThat(emailService.sendAttendanceEmail(EMAIL, emailData)).isTrue();
    }

    @Test
    public void EmailService_SendNonAttendanceEmail_ReturnsTrue() {
        Context emailData = new Context();
        emailData.setVariable("users", new String[]{ NAME });
        emailData.setVariable("date", DATE_TIME.format(DATE_FORMATTER));
        emailData.setVariable("time", DATE_TIME.format(TIME_FORMATTER));

        when(templateEngine.process("non_attendance_email", emailData)).thenReturn("Email body content");

        Assertions.assertThat(emailService.sendNonAttendanceEmail(EMAIL, emailData)).isTrue();
    }

    @Test
    public void EmailService_SendUnauthorizedLoginEmail_ReturnsTrue() {
        Context emailData = new Context();
        emailData.setVariable("email", EMAIL);
        emailData.setVariable("date", DATE_TIME.format(DATE_FORMATTER));
        emailData.setVariable("time", DATE_TIME.format(TIME_FORMATTER));

        when(templateEngine.process("unauthorized_login_email", emailData)).thenReturn("Email body content");

        Assertions.assertThat(emailService.sendUnauthorizedLoginEmail(EMAIL, emailData)).isTrue();
    }
}
