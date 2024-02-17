package com.engeto.project3.clientmanagement.config.email;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Component
public class LicenseFileWriter implements ItemWriter<ClientLicense> {

    private static final String FILE_PATH = "licenses.txt";

    @Override
    public void write(Chunk<? extends ClientLicense> clientLicenses) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            for (ClientLicense clientLicense : clientLicenses) {
                ClientLicenseId clientLicenseId = clientLicense.getClientlicenseId();
                ClientInfo client = clientLicenseId.getClient();
                LicenseForSW licenseForSW = clientLicenseId.getLicense();

                writer.write("Client: " + client.getClientName() + "\n");
                writer.write("Software: " + licenseForSW.getSoftwareName() + "\n");
                writer.write("License Key: " + licenseForSW.getLicenseKey() + "\n");
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
