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
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    }  //TODO how mock persistence.EntityManager

    @Test
    void createLicense() {
    }

    @Test
    void validationClient() {
    }

    @Test
    void deleteClientLicenseByNameAndSwName() {
    }
}