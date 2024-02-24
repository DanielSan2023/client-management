package com.engeto.project3.clientmanagement.config.email;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.test.context.SpringBatchTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBatchTest
@ExtendWith(MockitoExtension.class)
class LicenseProcessorTest {

    @Mock
    private SendEmailStep sendEmailStep;

    @Test
    public void testProcess() throws Exception {
        // GIVEN
        MockitoAnnotations.initMocks(this);
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");

        ClientLicenseId clientLicenseId = new ClientLicenseId(client, licenseForSW);
        ClientLicense clientLicense = new ClientLicense();
        clientLicense.setClientlicenseId(clientLicenseId);

        // WHEN
        LicenseProcessor licenseProcessor = new LicenseProcessor(sendEmailStep);
        ClientLicense processedLicense = licenseProcessor.process(clientLicense);

        // THEN
        verify(sendEmailStep,times(1)).sendEmailToClient(client, licenseForSW);
        assertEquals(clientLicense, processedLicense);
        assertThat(processedLicense).isEqualTo(clientLicense);
    }
}

