package com.engeto.project3.clientmanagement.config.email;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.test.context.SpringBatchTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBatchTest
@ExtendWith(MockitoExtension.class)
class LicenseReaderTest {

    @Mock
    private ClientLicenseRepository clientLicenseRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void GIVEN_licenses_WHEN_testRead_THEN_return_licenses() throws Exception {
        // GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");
        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Linux", "someLinuxKey");

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(LocalDateTime.now());

        ClientLicense license2 = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW2));
        license.setStartDate(LocalDateTime.now());

        List<ClientLicense> licenses = Arrays.asList(license, license2);

        when(clientLicenseRepository.findAll()).thenReturn(licenses);

        // WHEN
        LicenseReader reader = new LicenseReader(clientLicenseRepository);
        ClientLicense result = reader.read();
        ClientLicense result2 = reader.read();

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getClientlicenseId().getClient()).isEqualTo(client);
        assertThat(result.getClientlicenseId().getLicense()).isEqualTo(licenseForSW2);
        assertThat(result2).isNotNull();
        assertThat(result2).isEqualTo(license2);
    }

    @Test
    public void GIVEN_empty_DB_WHEN_testReadWithEmptyResult_THEN_return_null() throws Exception {
        // GIVEN

        // WHEN
        LicenseReader reader = new LicenseReader(clientLicenseRepository);
        ClientLicense license = reader.read();

        // THEN
        assertThat(license).isNull();
    }
}