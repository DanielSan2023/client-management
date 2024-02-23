package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import com.engeto.project3.clientmanagement.service.ClientInfoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {
    @Mock
    private ClientInfoService clientInfoService;

    @Mock
    private ClientInfoRepository clientInfoRepository;

    @InjectMocks
    private ClientController clientController;

    @Test
    void GIVEN_mocked_client_as_null_WHEN_createClient_THEN_error() {
        //GIVEN
        when(clientInfoService.createClient(any())).thenReturn(null);

        //WHEN
        ResponseEntity<ClientDto> returnValue = clientController.createClient(new ClientDto());

        //THEN
        assertThat(returnValue.getBody()).isNull();
    }

    @Test
    void GIVEN_mocked_client_WHEN_createClient_THEN_client_Created() {
        //GIVEN
        when(clientInfoService.createClient(any())).thenReturn(new ClientDto());

        //WHEN
        ResponseEntity<ClientDto> returnValue = clientController.createClient(new ClientDto());

        //THEN
        assertThat(returnValue.getBody()).isNotNull();
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void GIVEN_mocked_client_invalid_name_WHEN_getClientByName_THEN_NOT_FOUND() {
        //GIVEN
        String clientName = "Nicolas";
        when(clientInfoService.getClientByName(any())).thenReturn(null);

        //WHEN
        ResponseEntity<ClientDto> returnValue = clientController.getClientByName(clientName);

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(returnValue.getBody()).isNull();
    }

    @Test
    void GIVEN_mocked_client_WHEN_getClientByName_THEN_returned_client() {
        //GIVEN
        String clientName = "Nicolas";
        when(clientInfoService.getClientByName(any())).thenReturn(new ClientDto());

        //WHEN
        ResponseEntity<ClientDto> returnValue = clientController.getClientByName(clientName);

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(returnValue.getBody()).isNotNull();
    }

    @Test
    void GIVEN_mocked_client_WHEN_updateClientByClientName_THEN_return_updated_client_OK() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        String clientName = client.getClientName();

        ClientDto updatedClient = new ClientDto(1L, "Jackson", "Nexus", "LA", "jacksson@gmail.com");

        when(clientInfoRepository.findByClientName(clientName)).thenReturn(client);
        when(clientInfoService.updateClientByName(clientName, new ClientDto())).thenReturn(updatedClient);

        //WHEN
        ResponseEntity<ClientDto> returnValue = clientController.updateClientByClientName(clientName, new ClientDto());

        //THEN
        assertThat(returnValue).isNotNull();
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void GIVEN_mocked_client_invalid_name_WHEN_updateClientByClientName_THEN_NOT_FOUND() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        String clientName = client.getClientName();

        when(clientInfoRepository.findByClientName(clientName)).thenReturn(null);

        //WHEN
        ResponseEntity<ClientDto> returnValue = clientController.updateClientByClientName(clientName, new ClientDto());

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void GIVEN_client_WHEN_deleteClient_THEN_returned_OK() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        String clientName = client.getClientName();

        when(clientInfoService.deleteClient(clientName)).thenReturn(client);

        //WHEN
        ResponseEntity<HttpStatus> returnValue = clientController.deleteClient(clientName);

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void GIVEN_client_invalid_name_WHEN_deleteClient_THEN_returned_OK() {
        //GIVEN
        ClientInfo client = new ClientInfo(1L, "Jackson", "IBM", "LA", "jacksson@gmail.com");
        String clientName = client.getClientName();

        when(clientInfoService.deleteClient(clientName)).thenReturn(null);

        //WHEN
        ResponseEntity<HttpStatus> returnValue = clientController.deleteClient(clientName);

        //THEN
        assertThat(returnValue.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}