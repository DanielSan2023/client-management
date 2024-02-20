package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientLicenseRepositoryTest {

    public static final LocalDateTime START_DATE_TEST = LocalDateTime.of(2024, 2, 20, 10, 30);

    @Autowired
    ClientInfoRepository clientInfoRepository;

    @Autowired
    ClientLicenseRepository clientLicenseRepository;

    @Autowired
    LicenseForSwRepository licenseForSwRepository;

    @BeforeEach
    void deleteDb() {
        clientLicenseRepository.deleteAll();
        clientInfoRepository.deleteAll();
        licenseForSwRepository.deleteAll();
    }

    @Test
    public void GIVEN_empty_db_WHEN_findByClientlicenseId_THEN_return_null() {
        //GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();
        ClientLicense clientLicense = new ClientLicense();

        //WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId(clientLicense.getClientlicenseId());

        //THEN
        assertThat(returnedLicense).isNull();
    }

    @Test
    public void GIVEN_clientLicense_WHEN_findByClientlicenseId_THEN_return_license() {
        //GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license);

        //WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId(license.getClientlicenseId());

        //THEN
        assertThat(returnedLicense.getClientlicenseId()).isEqualTo(license.getClientlicenseId());
        assertThat(returnedLicense.getStartDate()).isEqualTo(START_DATE_TEST);
    }

    @Test
    public void GIVEN_invalid_clientLicense_WHEN_findByClientlicenseId_THEN_return_null() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        ClientLicenseId invalid = new ClientLicenseId(client2, licenseForSW);

        // WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId(invalid);

        // THEN
        assertThat(returnedLicense).isNull();
    }

    @Test//TODO vvgb
    public void GIVEN_two_clientLicenses_WHEN_findByClientlicenseId_THEN_return_correct_license() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        // WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId(license1.getClientlicenseId());

        // THEN
        assertThat(returnedLicense).isNotNull();
        assertThat(returnedLicense.getClientlicenseId()).isEqualTo(license1.getClientlicenseId());

    }

    @Test
    public void GIVEN_empty_db_WHEN_findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_return_null() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        // WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("Jackson", "Windows");

        // THEN
        assertThat(returnedLicense).isNull();
    }

    @Test
    public void GIVEN_clientLicense_WHEN_findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_return_correct_license() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        // WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("Jackson", "Windows");

        // THEN
        assertThat(returnedLicense).isNotNull();
        assertThat(returnedLicense.getClientlicenseId()).isEqualTo(license1.getClientlicenseId());
        assertThat(returnedLicense.getStartDate()).isEqualTo(START_DATE_TEST);
    }

    @Test
    public void GIVEN_invalid_clientLicense_client_name_WHEN_findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_return_null() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        // WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("Trinity", "Windows");

        // THEN
        assertThat(returnedLicense).isNull();
    }

    @Test
    public void GIVEN_invalid_clientLicense_sw_name_WHEN_findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_return_null() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        // WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("Jackson", "someSW");

        // THEN
        assertThat(returnedLicense).isNull();
    }

    @Test
    public void GIVEN_clientLicenses_to_db_client_name_sw_name_WHEN_findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_return_correct_license() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        // WHEN
        ClientLicense returnedLicense = clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("invalidName", "invalidSW");

        // THEN
        assertThat(returnedLicense).isNull();
    }

    @Test
    public void GIVEN_one_license_WHEN_deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_db_empty() {
        //GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license);

        assertThat(clientLicenseRepository.findAll()).hasSize(1);

        //WHEN
        clientLicenseRepository.deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("Jackson", "Windows");

        //THEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();
    }

    @Test
    public void GIVEN_two_licenses_WHEN_deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_check_if_delete_correct_license() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        assertThat(clientLicenseRepository.findAll()).hasSize(2);

        //WHEN
        clientLicenseRepository.deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("Jackson", "Windows");

        //THEN
        assertThat(clientLicenseRepository.findAll()).hasSize(1);
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();
        assertThat(returnedLicenses).hasSize(1);
        assertThat(returnedLicenses.get(0).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Matrix");
        assertThat(returnedLicenses.get(0).getClientlicenseId().getClient().getClientName()).isEqualTo("Neo");
    }

    @Test
    public void GIVEN_license_invalid_client_name_WHEN_deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_check_if_deleted() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        assertThat(clientLicenseRepository.findAll()).hasSize(2);

        //WHEN
        clientLicenseRepository.deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("invalidName", "Windows");

        //THEN
        assertThat(clientLicenseRepository.findAll()).hasSize(2);
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();

        assertThat(returnedLicenses.get(0).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Windows");
        assertThat(returnedLicenses.get(0).getClientlicenseId().getClient().getClientName()).isEqualTo("Jackson");
        assertThat(returnedLicenses.get(1).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Matrix");
        assertThat(returnedLicenses.get(1).getClientlicenseId().getClient().getClientName()).isEqualTo("Neo");
    }

    @Test
    public void GIVEN_license_invalid_sw_name_WHEN_deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_check_if_deleted() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        assertThat(clientLicenseRepository.findAll()).hasSize(2);

        //WHEN
        clientLicenseRepository.deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("Jackson", "invalidSwName");

        //THEN
        assertThat(clientLicenseRepository.findAll()).hasSize(2);
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();

        assertThat(returnedLicenses.get(0).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Windows");
        assertThat(returnedLicenses.get(0).getClientlicenseId().getClient().getClientName()).isEqualTo("Jackson");
        assertThat(returnedLicenses.get(1).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Matrix");
        assertThat(returnedLicenses.get(1).getClientlicenseId().getClient().getClientName()).isEqualTo("Neo");
    }

    @Test
    public void GIVEN_license_invalid_client_name_sw_name_WHEN_deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName_THEN_check_if_deleted() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        assertThat(clientLicenseRepository.findAll()).hasSize(2);

        //WHEN
        clientLicenseRepository.deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName("invalidName", "invalidSwName");

        //THEN
        assertThat(clientLicenseRepository.findAll()).hasSize(2);
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();

        assertThat(returnedLicenses.get(0).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Windows");
        assertThat(returnedLicenses.get(0).getClientlicenseId().getClient().getClientName()).isEqualTo("Jackson");
        assertThat(returnedLicenses.get(1).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Matrix");
        assertThat(returnedLicenses.get(1).getClientlicenseId().getClient().getClientName()).isEqualTo("Neo");
    }

    @Test
    public void GIVEN_empty_db_WHEN_deleteAllByClientlicenseId_Client_ClientName_THEN_return_nothing() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        //WHEN
        clientLicenseRepository.deleteAllByClientlicenseId_Client_ClientName("Jackson");

        //THEN
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();
        assertThat(returnedLicenses).isEmpty();
    }

    @Test
    public void GIVEN_license_WHEN_deleteAllByClientlicenseId_Client_ClientName_THEN_return_empty_db() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        //WHEN
        clientLicenseRepository.deleteAllByClientlicenseId_Client_ClientName("Jackson");

        //THEN
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();
        assertThat(returnedLicenses).isEmpty();
    }

    @Test
    public void GIVEN_license_invalid_client_name_WHEN_deleteAllByClientlicenseId_Client_ClientName_THEN_check_if_deleted() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        //WHEN
        clientLicenseRepository.deleteAllByClientlicenseId_Client_ClientName("invalidName");

        //THEN
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();
        assertThat(returnedLicenses.get(0).getClientlicenseId().getClient().getClientName()).isEqualTo("Jackson");
    }

    @Test
    public void GIVEN_two_licenses_WHEN_deleteAllByClientlicenseId_Client_ClientName_THEN_deleted_correct_license() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        LicenseForSW licenseForSW3 = new LicenseForSW(3L, "Linux", "linuxCode");
        licenseForSwRepository.save(licenseForSW3);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        ClientLicense license3 = new ClientLicense();
        license3.setClientlicenseId(new ClientLicenseId(client, licenseForSW3));
        license3.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license3);

        assertThat(clientLicenseRepository.findAll()).hasSize(3);

        //WHEN
        clientLicenseRepository.deleteAllByClientlicenseId_Client_ClientName("Jackson");

        //THEN
        assertThat(clientLicenseRepository.findAll()).hasSize(1);
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();

        assertThat(returnedLicenses.get(0).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Matrix");
        assertThat(returnedLicenses.get(0).getClientlicenseId().getClient().getClientName()).isEqualTo("Neo");
    }

    @Test
    public void GIVEN_two_licenses_invalid_client_name_WHEN_deleteAllByClientlicenseId_Client_ClientName_THEN_deleted_nothing() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        assertThat(clientLicenseRepository.findAll()).hasSize(2);

        //WHEN
        clientLicenseRepository.deleteAllByClientlicenseId_Client_ClientName("invalidName");

        //THEN
        assertThat(clientLicenseRepository.findAll()).hasSize(2);
        List<ClientLicense> returnedLicenses = clientLicenseRepository.findAll();

        assertThat(returnedLicenses.get(0).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Windows");
        assertThat(returnedLicenses.get(0).getClientlicenseId().getClient().getClientName()).isEqualTo("Jackson");
        assertThat(returnedLicenses.get(1).getClientlicenseId().getLicense().getSoftwareName()).isEqualTo("Matrix");
        assertThat(returnedLicenses.get(1).getClientlicenseId().getClient().getClientName()).isEqualTo("Neo");
    }

    @Test
    public void GIVEN_three_licenses_same_client_WHEN_findAllLicenseIdByClientName_THEN_return_List_with_two_licenses() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        LicenseForSW licenseForSW3 = new LicenseForSW(3L, "Linux", "linuxCode");
        licenseForSwRepository.save(licenseForSW3);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        ClientLicense license3 = new ClientLicense();
        license3.setClientlicenseId(new ClientLicenseId(client, licenseForSW3));
        license3.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license3);

        assertThat(clientLicenseRepository.findAll()).hasSize(3);

        //WHEN
        List<Long> returnedLicensesId = clientLicenseRepository.findAllLicenseIdByClientName("Jackson");

        //THEN
        assertThat(clientLicenseRepository.findAll()).hasSize(3);
        assertThat(returnedLicensesId).hasSize(2);
    }

    @Test
    public void GIVEN_two_licenses_invalid_client_name_WHEN_findAllLicenseIdByClientName_THEN_return_List_empty() {
        // GIVEN
        assertThat(clientLicenseRepository.findAll()).isEmpty();

        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        clientInfoRepository.save(client);

        ClientInfo client2 = new ClientInfo(2L, "Neo", "Matrix", "MW", "neo@gmail.com");
        clientInfoRepository.save(client2);

        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");
        licenseForSwRepository.save(licenseForSW);

        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Matrix", "matrixCode");
        licenseForSwRepository.save(licenseForSW2);

        LicenseForSW licenseForSW3 = new LicenseForSW(3L, "Linux", "linuxCode");
        licenseForSwRepository.save(licenseForSW3);

        ClientLicense license1 = new ClientLicense();
        license1.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license1.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license1);

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client2, licenseForSW2));
        license2.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license2);

        ClientLicense license3 = new ClientLicense();
        license3.setClientlicenseId(new ClientLicenseId(client, licenseForSW3));
        license3.setStartDate(START_DATE_TEST);
        clientLicenseRepository.save(license3);

        assertThat(clientLicenseRepository.findAll()).hasSize(3);

        //WHEN
        List<Long> returnedLicensesId = clientLicenseRepository.findAllLicenseIdByClientName("invalidName");

        //THEN
        assertThat(clientLicenseRepository.findAll()).hasSize(3);
        assertThat(returnedLicensesId).isEmpty();
    }
}
