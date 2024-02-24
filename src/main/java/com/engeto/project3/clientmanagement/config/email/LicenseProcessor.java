package com.engeto.project3.clientmanagement.config.email;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
@StepScope
@Component
public class LicenseProcessor implements ItemProcessor<ClientLicense, ClientLicense> {

    SendEmailStep sendEmailStep;

    public LicenseProcessor(SendEmailStep sendEmailStep) {
        this.sendEmailStep = sendEmailStep;
    }

    @Override
    public ClientLicense process(ClientLicense clientLicense) throws Exception {

        ClientLicenseId clientLicenseId = clientLicense.getClientlicenseId();
        ClientInfo client = clientLicenseId.getClient();
        LicenseForSW licenseForSW = clientLicenseId.getLicense();

        sendEmailStep.sendEmailToClient(client, licenseForSW);

        return clientLicense;
    }
}
