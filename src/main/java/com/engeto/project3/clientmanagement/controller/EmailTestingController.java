package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.config.email.SendEmailStep;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/send-test-email")
public class EmailTestingController {

    private final SendEmailStep sendEmailStep;

    @RequestMapping
    public String sentEmailTest() {
        sendEmailStep.sendEmailToClient();
        return null;
    }
}
