package com.engeto.project3.clientmanagement.task;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import com.engeto.project3.clientmanagement.converter.AESEncryptionDecryption;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Component
public class KeyGenerationTask {

    private ClientLicenseRepository clientLicenseRepository;
    private AESEncryptionDecryption encryptDecrypt;

    @Scheduled(cron = "0 0 0 * * MON")
    @Transactional
    public void generateKeysForClients() {
        List<ClientLicense> clientLicenses = clientLicenseRepository.findAll();

        for (ClientLicense clientLicense : clientLicenses) {
            ClientInfo client = clientLicense.getClientlicenseId().getClient();
            LicenseForSW license = clientLicense.getClientlicenseId().getLicense();

            String clientName = client.getClientName();
            String swName = license.getSoftwareName();

            String newLicenseKey = generateNewKey(clientName, swName);
            license.setLicenseKey(newLicenseKey);

            clientLicenseRepository.save(clientLicense);
        }
    }

    private String generateNewKey(String clientName, String swName) {
        String secret = "secrete";
        String originalStrings = clientName + swName + LocalDate.now();
        return encryptDecrypt.encrypt(originalStrings, secret);
    }
}
