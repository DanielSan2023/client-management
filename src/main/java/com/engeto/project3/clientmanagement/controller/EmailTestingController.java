package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.config.email.SendEmailStep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/send-test-email")
public class EmailTestingController {

    private final SendEmailStep sendEmailStep;

    public EmailTestingController(SendEmailStep sendEmailStep) {
        this.sendEmailStep = sendEmailStep;
    }

    @Value("${email.send.enable}")
    private boolean sendEmail;

    @RequestMapping
    public void sentEmailTest() {
        if (sendEmail) {
            log.error("Send email is not supported on production. ");
        } else {
            sendEmailStep.sendEmailToClient();
            log.error("Sending an email. ");
        }
    }
}
