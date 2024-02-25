package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.converter.StringToClientLicenseIdConverter;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class ClientLicenseControllerTest {

    @Mock
    ClientLicenseId clientLicenseId;

    @Mock
    StringToClientLicenseIdConverter stringToClientLicenseIdConverter;

    @Mock
    ClientLicenseService clientLicenseService;

    @InjectMocks
    ClientLicenseController clientLicenseController;

    @Test
    void GIVEN_license_Id_WHEN_fetchClientLicenseById_THEN_return_license_OK() {
        //GIVEN
        String id = "1-1";
        ClientLicenseId licenseId = new ClientLicenseId();

        when(stringToClientLicenseIdConverter.convert(id)).thenReturn(licenseId);
        when(clientLicenseService.getClientLicenseById(any())).thenReturn(new ClientLicenseDto());
        //WHEN
        ResponseEntity<ClientLicenseDto> result = clientLicenseController.fetchClientLicenseById(id);

        //THEN
        assertThat(result.getBody()).isNotNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void GIVEN_license_Id_WHEN_fetchClientLicenseById_THEN_return_NOT_FOUND() {
        //GIVEN
        String id = "1-1";
        ClientLicenseId licenseId = new ClientLicenseId();

        when(stringToClientLicenseIdConverter.convert(id)).thenReturn(licenseId);
        when(clientLicenseService.getClientLicenseById(any())).thenReturn(null);
        //WHEN
        ResponseEntity<ClientLicenseDto> result = clientLicenseController.fetchClientLicenseById(id);

        //THEN
        assertThat(result.getBody()).isNull();
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void GIVEN_client_license_dto_WHEN_createLicenseForClient_returned_saved_license_CREATED() {
        //GIVEN
        ClientLicenseDto licenseDto = new ClientLicenseDto();
        when(clientLicenseService.createLicense(licenseDto)).thenReturn(licenseDto);

        //WHEN
        ResponseEntity<ClientLicenseDto> result = clientLicenseController.createLicenseForClient(licenseDto);

        //THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(result).isNotNull();
    }

    @Test
    void GIVEN_List_licenses_WHEN_fetchAllClientLicence_THEN_return_list_OK() {
        //GIVEN
        List<ClientLicenseDto> licenseDtoes = Arrays.asList(new ClientLicenseDto(), new ClientLicenseDto());
        when(clientLicenseService.getAllClientLicenses()).thenReturn(licenseDtoes);

        //WHEN
        ResponseEntity<List<ClientLicenseDto>> resultList = clientLicenseController.fetchAllClientLicence();

        //THEN
        assertThat(resultList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultList).isNotNull();
    }

    @Test
    void GIVEN_empty_list_licenses_WHEN_fetchAllClientLicence_THEN_return_NOT_FOUND() {
        //GIVEN
        when(clientLicenseService.getAllClientLicenses()).thenReturn(null);

        //WHEN
        ResponseEntity<List<ClientLicenseDto>> resultList = clientLicenseController.fetchAllClientLicence();

        //THEN
        assertThat(resultList.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(resultList.getBody()).isNull();
    }

    @Test
    void GIVEN_List_licenses_WHEN_fetchAllClientLicenceByClientName_THEN_return_list_OK() {
        //GIVEN
        String clientName = "someName";
        List<ClientLicenseDto> licenseDtoes = Arrays.asList(new ClientLicenseDto(), new ClientLicenseDto());
        when(clientLicenseService.getAllClientLicensesByClientName(clientName)).thenReturn(licenseDtoes);

        //WHEN
        ResponseEntity<List<ClientLicenseDto>> resultList = clientLicenseController.fetchAllClientLicenceByClientName(clientName);

        //THEN
        assertThat(resultList.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(resultList).isNotNull();
    }

    @Test
    void GIVEN_empty_list_licenses_WHEN_fetchAllClientLicenceByClientName_THEN_return_NOT_FOUND() {
        //GIVEN
        String clientName = "someName";
        when(clientLicenseService.getAllClientLicensesByClientName(clientName)).thenReturn(null);

        //WHEN
        ResponseEntity<List<ClientLicenseDto>> resultList = clientLicenseController.fetchAllClientLicenceByClientName(clientName);

        //THEN
        assertThat(resultList.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(resultList.getBody()).isNull();
    }

    @Test
    void GIVEN_licenses_WHEN_deleteClientLicenseByNameAndSwName_THEN_verify() {
        //GIVEN
        ClientLicenseDto licenseDto = new ClientLicenseDto();

        //WHEN
        clientLicenseController.deleteClientLicenseByNameAndSwName(licenseDto.getName(), licenseDto.getSoftwareName());

        //THEN
        verify(clientLicenseService, times(1)).deleteClientLicenseByNameAndSwName(licenseDto.getName(), licenseDto.getSoftwareName());
    }
}