package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientInfoServiceTest {

    @Mock
    ModelMapper modelMapper;

    @Mock
    private ClientInfoRepository clientInfoRepository;

    @Mock
    private ClientLicenseRepository clientLicenseRepository;

    @InjectMocks
    private ClientInfoService clientInfoService;

    @BeforeEach
    void deleteDb() {
        clientInfoRepository.deleteAll();
    }

    @Test
    void GIVEN_client_Dto_same_name_WHEN_createClient_THEN_save_not_called() {
        //GIVEN
        ClientDto clientDto = new ClientDto(1L, "Neo", "Matrix", "Universe", "neo@matric.net");

        when(clientInfoRepository.existsByClientNameIgnoreCase(clientDto.getClientName())).thenReturn(true);

        //WHEN
        assertThrows(RuntimeException.class, () -> clientInfoService.createClient(clientDto));

        //THEN
        Mockito.verify(clientInfoRepository, times(0)).save(any());
    }

    @Test
    void GIVEN_client_Dto_WHEN_createClient_THEN_save_called_once() {
        //GIVEN
        ClientDto clientDto = new ClientDto(1L, "Neo", "Matrix", "Universe", "neo@matric.net");
        ClientInfo client = new ClientInfo(1L, "Neo", "Matrix", "Universe", "neo@matric.net");

        when(clientInfoRepository.existsByClientNameIgnoreCase(clientDto.getClientName())).thenReturn(false);
        when(clientInfoRepository.save(Mockito.any())).thenReturn(client);

        //WHEN
        ClientDto savedClientDto = clientInfoService.createClient(clientDto);

        //THEN
        Mockito.verify(clientInfoRepository, times(1)).save(any());
        assertThat(savedClientDto).isNotNull();
    }

    @Test
    void GIVEN_client_name_WHEN_getClientByName_THEN_return_client() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Neo", "Matrix", "Universe", "neo@matric.net");
        ClientDto clientDto = new ClientDto(1L, "Neo", "Matrix", "Universe", "neo@matric.net");

        when(clientInfoRepository.findByClientName(clientDto.getClientName())).thenReturn(client);
        when(modelMapper.map(any(), any())).thenReturn(clientDto);
        //WHEN
        ClientDto retrievedClient = clientInfoService.getClientByName(clientDto.getClientName());

        //THEN
        Mockito.verify(clientInfoRepository, times(1)).findByClientName(any());
        assertThat(retrievedClient).isEqualTo(clientDto);
    }

    @Test
    void GIVEN_client_invalid_name_WHEN_getClientByName_THEN_return_null() {
        //GIVEN
        ClientDto clientDto = new ClientDto(1L, "Neo", "Matrix", "Universe", "neo@matric.net");

        when(clientInfoRepository.findByClientName(clientDto.getClientName())).thenReturn(null);

        //WHEN
        ClientDto retrievedClient = clientInfoService.getClientByName(clientDto.getClientName());

        //THEN
        Mockito.verify(clientInfoRepository, times(1)).findByClientName(any());
        assertThat(retrievedClient).isNull();
    }

    @Test
    void GIVEN_clientDto_WHEN_updateClientByName_THEN_return_updated_clientDto() {
        //GIVEN
        ClientDto clientDto = new ClientDto(1L, "Neo", "Matrix", "Universe", "neo@matric.net");
        ClientInfo client = new ClientInfo(1L, "Neo", "MatrixReloaded", "Universe", "neo@matric.net");

        when(clientInfoRepository.findByClientName("Neo")).thenReturn(client);
        when(clientInfoRepository.save(client)).thenReturn(client);
        when(modelMapper.map(any(), any())).thenReturn(clientDto);
        //WHEN
        ClientDto updatedClient = clientInfoService.updateClientByName("Neo", clientDto);

        //THEN
        verify(clientInfoRepository, times(1)).save(any());
        assertThat(updatedClient).isNotNull();
    }

    @Test
    void GIVEN_client_name_WHEN_deleteClient_THEN_remove_client_license_sw_license() {
        // GIVEN
        ClientDto clientDto = new ClientDto(1L, "Neo", "Matrix", "Universe", "neo@matric.net");
        ClientInfo client = new ClientInfo(1L, "Neo", "MatrixReloaded", "Universe", "neo@matric.net");
        String clientName = "Neo";

        List<Long> licenseIds = Arrays.asList(1L, 2L, 3L);
        when(clientLicenseRepository.findAllLicenseIdByClientName(clientName)).thenReturn(licenseIds);

        // WHEN
        doNothing().when(clientInfoRepository).delete(client);
        clientInfoService.deleteClient(clientName);

        // THEN
        //        verify(clientInfoRepository, times(1)).deleteByClientName(clientName);
        //        verify(clientInfoRepository, times(1)).delete(any());
    } //TODO fixit delete method

}