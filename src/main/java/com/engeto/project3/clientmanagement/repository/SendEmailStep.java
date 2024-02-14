package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class SendEmailStep {

    private final JavaMailSender javaMailSender;
    private final ClientLicenseService clientLicenseService;

    public SendEmailStep(JavaMailSender javaMailSender, ClientLicenseService clientLicenseService) {
        this.javaMailSender = javaMailSender;
        this.clientLicenseService = clientLicenseService;
    }

    @Bean
    public Step sendEmailStep() {
        return new StepBuilder("sendEmailStep")
                .<ClientInfo, ClientInfo>chunk(10)
                .reader(clientLicenseService.getAllClient())
                .processor(item -> {
                    sendEmailToClient(item);
                    return item;
                })
                .build();
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

