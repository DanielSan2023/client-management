package com.engeto.project3.clientmanagement.config;

import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Configuration
public class SendEmailStep {

    private final JavaMailSender javaMailSender;
    private final ClientLicenseService clientLicenseService;

    public SendEmailStep(JavaMailSender javaMailSender, ClientLicenseService clientLicenseService) {
        this.javaMailSender = javaMailSender;
        this.clientLicenseService = clientLicenseService;
    }

    public void sendEmailToClient() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("electronic.san@gmail.com");
        message.setSubject("Your Weekly Update");
        message.setText("This is first mail.");
        // message.setText("Dear " + client.getClientName() + ",\n\nThis is your weekly update.\n\nRegards,\nYour Company");

        javaMailSender.send(message);
    }
}

