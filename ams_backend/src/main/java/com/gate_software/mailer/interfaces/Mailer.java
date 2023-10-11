package com.gate_software.mailer.interfaces;

public interface Mailer {
    public void sendEmail(String toEmailAddress, String subject, String bodyText);
}
