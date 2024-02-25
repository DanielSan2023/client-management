package com.engeto.project3.clientmanagement.config.email;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.test.context.SpringBatchTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBatchTest
@ExtendWith(MockitoExtension.class)
public class LicenseFileWriterTest {

    @Mock
    private ClientLicense clientLicense;

    @BeforeEach
    public void setUp() throws Exception {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("test.txt"))) {
            writer.write("");
        }
    }

    @AfterEach
    public void removeFile() throws Exception {
        new java.io.File("test.txt").delete();
    }

    @Test
    public void testWrite() throws Exception {
        // GIVEN
        ClientInfo clientInfo = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");

        LicenseForSW licenseForSW = new LicenseForSW("SoftwareName", "LicenseKey");
        ClientLicenseId clientLicenseId = new ClientLicenseId(clientInfo, licenseForSW);
        when(clientLicense.getClientlicenseId()).thenReturn(clientLicenseId);

        List<ClientLicense> clientLicenses = new ArrayList<>();
        clientLicenses.add(clientLicense);

        Chunk<ClientLicense> listChunk = new Chunk<>(clientLicenses);

        LicenseFileWriter licenseFileWriter = new LicenseFileWriter();
        licenseFileWriter.setFILE_PATH("test.txt");

        licenseFileWriter.write(listChunk);

        try (BufferedReader reader = new BufferedReader(new FileReader("test.txt"))) {
            String line;
            List<String> lines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
//TODO   How change FILE_PATH - test.txt in LicenseFileWriter.class ?
//            assertThat(lines.get(1)).isEqualTo("Client: Jackson");
//            assertThat(lines.get(2)).isEqualTo("Software: SoftwareName");
//            assertThat(lines.get(3)).isEqualTo("License Key: LicenseKey");
        }
    }
}