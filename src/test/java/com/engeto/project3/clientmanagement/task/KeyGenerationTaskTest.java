package com.engeto.project3.clientmanagement.task;

import com.engeto.project3.clientmanagement.converter.AESEncryptionDecryption;
import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class KeyGenerationTaskTest {
    public static final LocalDateTime START_DATE_TEST = LocalDateTime.of(2024, 2, 20, 10, 30);

    @Mock
    private ClientLicenseRepository clientLicenseRepository;

    @Mock
    private AESEncryptionDecryption encryptDecrypt;

    private KeyGenerationTask keyGenerationTask;

    @BeforeEach
    void setUp() {
        keyGenerationTask = new KeyGenerationTask(clientLicenseRepository, encryptDecrypt);
    }

    @Test
    void generateKeysForClients_ShouldGenerateKeysForEachClientLicense() {
        // GIVEN
        List<ClientLicense> clientLicenses = new ArrayList<>();
        ClientInfo client = new ClientInfo(1L, "John Doe", "Company", "Location", "john.doe@example.com");
        LicenseForSW license = new LicenseForSW(1L, "Software", "LicenseKey");

        String expectedKey = "encryptedKey";

        ClientLicense clientLicense = new ClientLicense();
        clientLicense.setClientlicenseId(new ClientLicenseId(client, license));
        clientLicenses.add(clientLicense);

        when(clientLicenseRepository.findAll()).thenReturn(clientLicenses);

        when(encryptDecrypt.encrypt(anyString(), anyString())).thenReturn(expectedKey);

        // WHEN
        keyGenerationTask.generateKeysForClients();

        // THEN
        verify(clientLicenseRepository, times(1)).findAll();
        verify(encryptDecrypt, times(1)).encrypt("John DoeSoftware" + LocalDate.now(), "secrete");
        verify(clientLicenseRepository, times(1)).save(clientLicense);
        assertThat(expectedKey).isEqualTo(license.getLicenseKey());
    }
}