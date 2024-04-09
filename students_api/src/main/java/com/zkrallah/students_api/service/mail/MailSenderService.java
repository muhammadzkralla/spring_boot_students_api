package com.zkrallah.students_api.service.mail;

public interface MailSenderService {
    void sendEmail(String toEmail, String subject, String body);
}
