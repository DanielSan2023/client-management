package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.converter.StringToClientLicenseIdConverter;
import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.service.ClientLicenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/license")
public class ClientLicenseController {

    final private ClientLicenseService clientLicenseService;
    final private StringToClientLicenseIdConverter stringToClientLicenseIdConverter;

    public ClientLicenseController(ClientLicenseService clientLicenseService, StringToClientLicenseIdConverter stringToClientLicenseIdConverter) {
        this.clientLicenseService = clientLicenseService;
        this.stringToClientLicenseIdConverter = stringToClientLicenseIdConverter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientLicenseDto> fetchClientLicenseById(@PathVariable String id) {
        ClientLicenseId ids = stringToClientLicenseIdConverter.convert(id);
        ClientLicenseDto clientLicenseDto = clientLicenseService.getClientLicenseById(ids);
        if (clientLicenseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(clientLicenseDto, HttpStatus.OK);
        }
    }

    @PostMapping("/save")
    public void saveLicense() {
        clientLicenseService.saveClientLicense();
    }

    @PostMapping("/create")
    public ResponseEntity<ClientLicenseDto> createLicenseForClient(@RequestBody ClientLicenseDto clientLicenseDto) {
        ClientLicenseDto createdLicenseDto = clientLicenseService.createLicense(clientLicenseDto);
        return new ResponseEntity<>(createdLicenseDto, HttpStatus.CREATED);
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