package com.engeto.project3.clientmanagement;

import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import com.engeto.project3.clientmanagement.repository.LicenseForSwRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpMethod.PUT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTestClient {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientLicenseRepository clientLicenseRepository;

    @Autowired
    private LicenseForSwRepository licenseForSwRepository;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    @BeforeEach
    void deleteDb() {
        clientLicenseRepository.deleteAll();
        clientInfoRepository.deleteAll();
        licenseForSwRepository.deleteAll();
    }

    @Test
    void GIVEN_clients_DB_WHEN_all_client_controller_request_THEN_all_worked_correctly() {
        // Checked DB
        assertThat(clientInfoRepository.findAll()).hasSize(0);

        //GIVEN client to db
        ClientDto clientDto = new ClientDto("Jackson", "Walt Disney", "LA", "jackson@gmail.com");

        // WHEN createClient THEN  checked DB
        ResponseEntity<ClientDto> createdClient = restTemplate.postForEntity(
                "http://localhost:" + port + "/client/save", clientDto, ClientDto.class);

        assertThat(clientInfoRepository.findAll()).hasSize(1);

        // WHEN getClientByName  THEN checked client
        ResponseEntity<ClientDto> savedClientDto = restTemplate.getForEntity(
                "http://localhost:" + port + "/client/{name}",
                ClientDto.class, clientDto.getClientName());

        assertThat(savedClientDto).isNotNull();

        //WHEN updateClientByClientName  THEN checked updated client data
        clientDto.setCompanyName("Warner Bros");
        ResponseEntity<ClientDto> client = restTemplate.exchange(
                "http://localhost:" + port + "/client/{clientName}", PUT, new HttpEntity<>(clientDto),
                ClientDto.class, (clientDto.getClientName()), clientDto, ClientDto.class);

        ResponseEntity<ClientDto> updatedClient = restTemplate.getForEntity(
                "http://localhost:" + port + "/client/{name}",
                ClientDto.class, Objects.requireNonNull(createdClient.getBody()).getClientName());

        assertThat(Objects.requireNonNull(updatedClient.getBody()).getCompanyName()).isEqualTo("Warner Bros");

        // WHEN deleted client  THEN  DB is empty
        ResponseEntity<HttpStatus> status = restTemplate.exchange("http://localhost:" + port + "/client/{name}", HttpMethod.DELETE,
                null, HttpStatus.class, clientDto.getClientName());

        assertThat(clientInfoRepository.findAll()).isEmpty();

    }
}