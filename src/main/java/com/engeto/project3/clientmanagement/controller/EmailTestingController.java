package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.config.email.SendEmailStep;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send-test-email")
public class EmailTestingController {

    private final SendEmailStep sendEmailStep;

    public EmailTestingController(SendEmailStep sendEmailStep) {
        this.sendEmailStep = sendEmailStep;
    }

    @RequestMapping
    public String sentEmailTest() {
        sendEmailStep.sendEmailToClient();
        return null;
    }
}
