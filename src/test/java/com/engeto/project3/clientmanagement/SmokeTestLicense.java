package com.engeto.project3.clientmanagement;

import com.engeto.project3.clientmanagement.controller.ClientController;
import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import com.engeto.project3.clientmanagement.repository.LicenseForSwRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTestLicense {

    public static final LocalDateTime START_DATE_TEST = LocalDateTime.of(2015, 05, 5, 12, 00);

    @LocalServerPort
    private int port;
    @Autowired
    ClientController clientController;

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
    void GIVEN_client_license_WHEN_call_ClientLicense_request_THEN_all_working_correctly() {
        //GIVEN  client
        ClientDto clientDto = new ClientDto(1L, "Jeremy", "IBM", "LA", "jackson@gmail.com");

        ResponseEntity<ClientDto> createdClient = restTemplate.postForEntity(
                "http://localhost:" + port + "/client/save", clientDto, ClientDto.class);

        // GIVEN clientLicenses for same client
        ClientLicenseDto licenseDto1 = new ClientLicenseDto();
        licenseDto1.setName(clientDto.getClientName());
        licenseDto1.setSoftwareName("Windows");
        licenseDto1.setLicenseKey("someKey");

        ClientLicenseDto licenseDto2 = new ClientLicenseDto();
        licenseDto2.setName(clientDto.getClientName());
        licenseDto2.setSoftwareName("Linux");
        licenseDto2.setLicenseKey("someKey");

        // WHEN  createLicenseForClient
        ResponseEntity<ClientLicenseDto> createdClientLicense1 = restTemplate.postForEntity(
                "http://localhost:" + port + "/license/create", licenseDto1, ClientLicenseDto.class);

        ResponseEntity<ClientLicenseDto> createdClientLicense2 = restTemplate.postForEntity(
                "http://localhost:" + port + "/license/create", licenseDto2, ClientLicenseDto.class);

        // THEN check exist licenses in db
        assertThat(createdClientLicense1).isNotNull();
        assertThat(createdClientLicense2).isNotNull();
        assertThat(clientLicenseRepository.findAll()).hasSize(2);

        // THEN fetchAllClientLicence
        ClientLicenseDto[] activeLicenses = restTemplate.getForObject(
                "http://localhost:" + port + "/license/active", ClientLicenseDto[].class);

        //THEN returned employees
        assertThat(activeLicenses).hasSize(2);
        assertThat(activeLicenses[0].getSoftwareName()).isEqualTo("Windows");
        assertThat(activeLicenses[1].getSoftwareName()).isEqualTo("Linux");

        String clientName = clientDto.getClientName();
        String swName = licenseDto1.getSoftwareName();

        // WHEN deleteClientLicenseByNameAndSwName
        ResponseEntity<Void> statusAfterDeleteLicense = restTemplate.exchange("http://localhost:" + port + "/license/" + clientName + "/" + swName, HttpMethod.DELETE,
                null, Void.class);

        // THEN check if remove correct license
        ClientLicenseDto[] activeLicense = restTemplate.getForObject(
                "http://localhost:" + port + "/license/active", ClientLicenseDto[].class);

        assertThat(activeLicense).hasSize(1);
        assertThat(activeLicense[0].getSoftwareName()).isEqualTo(licenseDto2.getSoftwareName());

        //WHEN deleteClient
        ResponseEntity<HttpStatus> statusRemoveClient = restTemplate.exchange("http://localhost:" + port + "/client/{name}", HttpMethod.DELETE,
                null, HttpStatus.class, clientName);

        //THEN remove all client licenses from DB
        assertThat(clientInfoRepository.findAll()).isEmpty();
        assertThat(clientLicenseRepository.findAll()).isEmpty();
    }
}
