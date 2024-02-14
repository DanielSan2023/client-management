package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmailStep {

    private final JavaMailSender javaMailSender;
    private final ClientLicenseService clientLicenseService;

    public SendEmailStep(JavaMailSender javaMailSender, ClientLicenseService clientLicenseService) {
        this.javaMailSender = javaMailSender;
        this.clientLicenseService = clientLicenseService;
    }

//    @Bean
//    public Step sendEmailStep(JobRepository jobRepository) {
//        return new StepBuilder("sendEmailStep",jobRepository)
//                .<ClientInfo, ClientInfo> // Process items in chunks of 10
//                .reader(clientLicenseService.getAllClient()) // Reader to fetch clients
//                .processor((item) -> { // Processor to handle the items
//                    emailService.sendEmailToClient(item);
//                    return item;
//                })
//                .build();
//    }

    public void sendEmailToClient() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("electronic.san@gmail.com");
        message.setSubject("Your Weekly Update");
        message.setText("This is first mail.");
        // message.setText("Dear " + client.getClientName() + ",\n\nThis is your weekly update.\n\nRegards,\nYour Company");

        javaMailSender.send(message);
    }
}

