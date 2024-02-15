package com.engeto.project3.clientmanagement.config.email;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
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

        javaMailSender.send(message);
    }

    public void sendEmailToClient(ClientInfo client, LicenseForSW license) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(client.getEmail());
        message.setSubject("LicenseKey");
        message.setText("Dear " + client.getClientName() + ",\n\nThis is your weekly update for " + license.getSoftwareName() + "\n\nkey: " + license.getLicenseKey());

        javaMailSender.send(message);
    }
}

