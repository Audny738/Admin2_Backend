package com.gate_software.ams_backend.service;

import com.gate_software.mailer.gmail.GmailMailer;
import com.gate_software.mailer.interfaces.Mailer;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Log4j2
public class EmailService {
    private static final Mailer MAILER = new GmailMailer();
    private static final String ATTENDANCE_TEMPLATE = "attendance_email";
    private static final String NON_ATTENDANCE_TEMPLATE = "non_attendance_email";
    private static final String UNAUTHORIZED_LOGIN_TEMPLATE = "unauthorized_login_email";
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ApplicationContext applicationContext;

    public boolean sendAttendanceEmail(String toEmailAddress, Context emailData) {
        String bodyText = templateEngine.process(ATTENDANCE_TEMPLATE, emailData);
        return MAILER.sendEmail(toEmailAddress, "Aviso de asistencia", bodyText);
    }

    public boolean sendNonAttendanceEmail(String toEmailAddress, Context emailData) {
        String bodyText = templateEngine.process(NON_ATTENDANCE_TEMPLATE, emailData);
        return MAILER.sendEmail(toEmailAddress, "Reporte de inasistencia", bodyText);
    }

    public boolean sendUnauthorizedLoginEmail(String toEmailAddress, Context emailData) {
        String bodyText = templateEngine.process(UNAUTHORIZED_LOGIN_TEMPLATE, emailData);
        return MAILER.sendEmail(toEmailAddress, "Aviso de intento de acceso no autorizado", bodyText);
    }
}

