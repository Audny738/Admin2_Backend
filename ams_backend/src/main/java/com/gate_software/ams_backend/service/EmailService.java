package com.gate_software.ams_backend.service;

import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import org.thymeleaf.TemplateEngine;
import org.springframework.context.ApplicationContext;
import org.thymeleaf.context.Context;

@Service
@Log4j2
public class EmailService {
    private static final String ATTENDANCE_TEMPLATE = "attendance_email";
    private static final String NON_ATTENDANCE_TEMPLATE = "non_attendance_email";
    private static final String UNAUTHORIZED_LOGIN_TEMPLATE = "unauthorized_login_email";
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private ApplicationContext applicationContext;

    public void sendAttendanceEmail(String to, Context context) throws MessagingException {
        String emailContent = templateEngine.process(ATTENDANCE_TEMPLATE, context);
        sendHtmlEmail(to, "Aviso de asistencia", emailContent);
    }

    public void sendNonAttendanceEmail(String to, Context context) throws MessagingException {
        String emailContent = templateEngine.process(NON_ATTENDANCE_TEMPLATE, context);
        sendHtmlEmail(to, "Reporte de inasistencia", emailContent);
    }

    public void sendUnauthorizedLoginEmail(String to, Context context) throws MessagingException {
        String emailContent = templateEngine.process(UNAUTHORIZED_LOGIN_TEMPLATE, context);
        sendHtmlEmail(to, "Aviso de intento de acceso no autorizado", emailContent);
    }

    private void sendHtmlEmail(String to, String subject, String emailContent) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(emailContent, true);

        emailSender.send(message);
    }
}

