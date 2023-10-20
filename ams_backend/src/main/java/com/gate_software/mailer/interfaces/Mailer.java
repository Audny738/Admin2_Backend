package com.gate_software.mailer.interfaces;

public interface Mailer {
    boolean sendEmail(String toEmailAddress, String subject, String bodyText);
}
