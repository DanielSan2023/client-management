package com.engeto.project3.clientmanagement;

import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SmokeTest {


    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ClientLicenseRepository clientLicenseRepository;

    @Autowired
    private ClientLicenseService clientLicenseService;





}
