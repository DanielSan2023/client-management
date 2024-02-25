package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.converter.AESEncryptionDecryption;
import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import com.engeto.project3.clientmanagement.repository.LicenseForSwRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class ClientLicenseServiceTest {
    public static final LocalDateTime START_DATE_TEST = LocalDateTime.of(2015, 05, 5, 12, 00);

    @Mock
    ModelMapper modelMapper;

    @Mock
    EntityManager entityManager;

    @Mock
    AESEncryptionDecryption encryptDecrypt;

    @Mock
    LicenseForSwRepository licenseRepository;

    @Mock
    ClientInfoRepository clientInfoRepository;

    @Mock
    private ClientLicenseRepository clientLicenseRepository;

    @InjectMocks
    private ClientLicenseService clientLicenseService;


    @Test
    void GIVEN_clientLicenseId_WHEN_getClientLicenseById_THEN_return_clientLicense() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(START_DATE_TEST);

        when(clientLicenseRepository.findByClientlicenseId(license.getClientlicenseId())).thenReturn(license);

        //WHEN
        ClientLicenseDto returnedLicense = clientLicenseService.getClientLicenseById(license.getClientlicenseId());

        //THEN
        assertThat(returnedLicense).isNotNull();
    }

    @Test
    void GIVEN_clientLicenseId_expiration_Date_WHEN_getClientLicenseById_THEN_return_clientLicense_isActive_false() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(START_DATE_TEST);

        when(clientLicenseRepository.findByClientlicenseId(license.getClientlicenseId())).thenReturn(license);

        //WHEN
        ClientLicenseDto returnedLicense = clientLicenseService.getClientLicenseById(license.getClientlicenseId());

        //THEN
        assertThat(returnedLicense).isNotNull();
        assertThat(returnedLicense.isActive()).isFalse();
    }

    @Test
    void GIVEN_clientLicenseId_non_expiration_Date_WHEN_getClientLicenseById_THEN_return_clientLicense_isActive_true() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(LocalDateTime.now());

        when(clientLicenseRepository.findByClientlicenseId(license.getClientlicenseId())).thenReturn(license);

        //WHEN
        ClientLicenseDto returnedLicense = clientLicenseService.getClientLicenseById(license.getClientlicenseId());

        //THEN
        assertThat(returnedLicense).isNotNull();
        assertThat(returnedLicense.isActive()).isTrue();
    }

    @Test
    void GIVEN_invalid_clientLicenseId_WHEN_getClientLicenseById_THEN_throw_Exception() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someLicenseKey");

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(START_DATE_TEST);

        when(clientLicenseRepository.findByClientlicenseId(license.getClientlicenseId())).thenReturn(null);

        //WHEN
        assertThrows(EntityNotFoundException.class, () -> clientLicenseService.getClientLicenseById(license.getClientlicenseId()));

        //THEN
        verify(clientLicenseRepository, times(1)).findByClientlicenseId(license.getClientlicenseId());
    }

    @Test
    void GIVEN_licenses_WHEN_getAllClientLicenses_THEN_return_List_clientLicenses_return_licenses() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");
        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Linux", "someLinuxKey");

        ClientLicenseDto licenseDto = new ClientLicenseDto();
        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(LocalDateTime.now());

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client, licenseForSW2));
        license2.setStartDate(LocalDateTime.now());

        List<ClientLicense> existLicenses = Arrays.asList(license, license2);
        when(clientLicenseRepository.findAll()).thenReturn(existLicenses);

        lenient().when(modelMapper.map(any(), any())).thenReturn(licenseDto);

        //WHEN
        List<ClientLicenseDto> returnedLicenses = clientLicenseService.getAllClientLicenses();

        //THEN
        assertThat(returnedLicenses).hasSize(2);
    }

    @Test
    void GIVEN_licenses_WHEN_getAllClientLicensesByClientName_THEN_return_List_clientLicenses_return_licenses() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");
        LicenseForSW licenseForSW2 = new LicenseForSW(2L, "Linux", "someLinuxKey");

        ClientLicenseDto licenseDto = new ClientLicenseDto();
        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(LocalDateTime.now());

        ClientLicense license2 = new ClientLicense();
        license2.setClientlicenseId(new ClientLicenseId(client, licenseForSW2));
        license2.setStartDate(LocalDateTime.now());

        List<ClientLicense> existLicenses = Arrays.asList(license, license2);
        when(clientLicenseRepository.findByClientlicenseId_Client_ClientName(client.getClientName())).thenReturn(existLicenses);

        lenient().when(modelMapper.map(any(), any())).thenReturn(licenseDto);

        //WHEN
        List<ClientLicenseDto> returnedLicenses = clientLicenseService.getAllClientLicensesByClientName(client.getClientName());

        //THEN
        assertThat(returnedLicenses).hasSize(2);
    }

    @Test
    void GIVEN_license_WHEN_createLicense_THEN_return_created_licenseDto() {
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");

        ClientDto clientDto = new ClientDto(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");

        ClientLicenseDto licenseDto = new ClientLicenseDto("Jackson", "Windows", "someWindowsKey", true);

        String originalString = client.getClientName() + licenseForSW.getSoftwareName() + LocalDate.now();
        String secretKey = "secrete";

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(LocalDateTime.now());

        lenient().when(modelMapper.map(any(), any())).thenReturn(licenseDto);
        when(clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(
                client.getClientName(), licenseForSW.getSoftwareName())).thenReturn(null);

        when(clientInfoRepository.findByClientName("")).thenReturn(client);
        when(clientLicenseService.validationClient(anyString())).thenReturn(client);


        when(encryptDecrypt.encrypt(originalString, secretKey)).thenReturn("someGeneratedKey");

        //WHEN
        ClientLicenseDto returnedLicense = clientLicenseService.createLicense(licenseDto);

        //THEN
        assertThat(returnedLicense).isNotNull();
    }

    @Test
    void GIVEN_license_exist_in_DB_WHEN_createLicense_THEN_throw_exception() {
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");

        ClientLicenseDto licenseDto = new ClientLicenseDto("Jackson", "Windows", "someWindowsKey", true);

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(LocalDateTime.now());

        when(clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(
                client.getClientName(), licenseForSW.getSoftwareName())).thenReturn(license);

        //WHEN & THEN
        assertThatThrownBy(() -> clientLicenseService.createLicense(licenseDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("License is already exist in DB!");
    }

    @Test
    void GIVEN_license_non_exist_account_in_DB_WHEN_createLicense_THEN_throw_exception() {
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");

        ClientLicenseDto licenseDto = new ClientLicenseDto("Jackson", "Windows", "someWindowsKey", true);

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(LocalDateTime.now());

        //WHEN & THEN
        assertThatThrownBy(() -> clientLicenseService.createLicense(licenseDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Client Jackson does not exist in database. You have to create client account!");
    }

    @Test
    void GIVEN_client_name_WHEN_validationClient_THEN_return_client() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        when(clientInfoRepository.findByClientName(client.getClientName())).thenReturn(client);

        //WHEN
        ClientInfo validClient = clientLicenseService.validationClient(client.getClientName());

        //THEN
        assertThat(validClient).isNotNull();
    }

    @Test
    void GIVEN_non_exist_client_name_WHEN_validationClient_THEN_throw_exception() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        when(clientInfoRepository.findByClientName(client.getClientName())).thenReturn(null);

        // WHEN & THEN
        assertThatThrownBy(() -> clientLicenseService.validationClient(client.getClientName()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Client " + client.getClientName() + " does not exist in database. You have to create client account!");

        //THEN
        verify(clientInfoRepository, times(1)).findByClientName(any());
    }

    @Test
    void GIVEN_license_WHEN_deleteClientLicenseByNameAndSwName_THEN_returns_void() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(START_DATE_TEST);

        when(clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(
                client.getClientName(), licenseForSW.getSoftwareName()))
                .thenReturn(license);

        doNothing().when(clientLicenseRepository).deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(client.getClientName(), licenseForSW.getSoftwareName());

        //WHEN
        clientLicenseService.deleteClientLicenseByNameAndSwName(client.getClientName(), licenseForSW.getSoftwareName());

        //THEN
        assertAll(() -> clientLicenseService.deleteClientLicenseByNameAndSwName(client.getClientName(), licenseForSW.getSoftwareName()));
    }

    @Test
    void GIVEN_non_exist_license_WHEN_deleteClientLicenseByNameAndSwName_THEN_returns_void() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        LicenseForSW licenseForSW = new LicenseForSW(1L, "Windows", "someWindowsKey");

        ClientLicense license = new ClientLicense();
        license.setClientlicenseId(new ClientLicenseId(client, licenseForSW));
        license.setStartDate(START_DATE_TEST);

        when(clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(
                client.getClientName(), licenseForSW.getSoftwareName()))
                .thenReturn(null);

        assertThatThrownBy(() -> clientLicenseService.deleteClientLicenseByNameAndSwName(client.getClientName(), licenseForSW.getSoftwareName()))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Client license doesnt exist in db!");
        //THEN
        verify(clientLicenseRepository, times(1)).findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(any(), any());
    }
}