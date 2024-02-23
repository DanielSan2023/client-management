package com.engeto.project3.clientmanagement.config.email;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

@ExtendWith(MockitoExtension.class)
class SendEmailStepTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private ClientLicenseService clientLicenseService;

    private SendEmailStep sendEmailStep;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        sendEmailStep = new SendEmailStep(javaMailSender, clientLicenseService);
    }

    @Test
    public void GIVEN_mail_WHEN_sendEmailToClient_THEN_verify() {
        // GIVEN
        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo("electronic.san@gmail.com");
        expectedMessage.setSubject("Your Weekly Update");
        expectedMessage.setText("This is first mail.");

        // WHEN
        sendEmailStep.sendEmailToClient();

        // THEN
        verify(javaMailSender).send(expectedMessage);
    }

    @Test
    public void  GIVEN_mail_WHEN_sendEmailToClient_THEN_verify_message() {
        // GIVEN
        ClientInfo client = new ClientInfo(1L, "John Doe", "Company", "Location", "john.doe@gmail.com");
        LicenseForSW license = new LicenseForSW(1L, "Software", "LicenseKey");

        SimpleMailMessage expectedMessage = new SimpleMailMessage();
        expectedMessage.setTo(client.getEmail());
        expectedMessage.setSubject("LicenseKey");
        expectedMessage.setText("Dear " + client.getClientName()
                + ",\n\nThis is your weekly update for " + license.getSoftwareName()
                + "\n\nkey: " + license.getLicenseKey());

        // WHEN
        sendEmailStep.sendEmailToClient(client, license);

        // THEN
        verify(javaMailSender).send(expectedMessage);
    }
}