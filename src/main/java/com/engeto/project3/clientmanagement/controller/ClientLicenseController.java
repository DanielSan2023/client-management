package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/license")
public class ClientLicenseController {

    final private ClientLicenseService clientLicenseService;

    public ClientLicenseController(ClientLicenseService clientLicenseService) {
        this.clientLicenseService = clientLicenseService;
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientLicenseDto> fetchClientLicenseById(@PathVariable ClientLicenseId clientId) {
        ClientLicenseDto clientLicenseDto = clientLicenseService.getClientLicenseById(clientId);
        if (clientLicenseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clientLicenseDto, HttpStatus.OK);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<List<ClientLicenseDto>> fetchAllClientLicence() {
        List<ClientLicenseDto> activeClientLicense = clientLicenseService.getAllClient();
        if (CollectionUtils.isEmpty(activeClientLicense)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(activeClientLicense, HttpStatus.OK);
        }
    }

}
