package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import com.engeto.project3.clientmanagement.service.ClientInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {
    final private ClientInfoRepository clientInfoRepository;
    final private ClientInfoService clientInfoService;

    public ClientController(ClientInfoRepository clientInfoRepository, ClientInfoService clientInfoService) {
        this.clientInfoRepository = clientInfoRepository;
        this.clientInfoService = clientInfoService;
    }

    @PostMapping("/save")
    public ResponseEntity<ClientDto> createClient(@RequestBody ClientDto clientDto) {
        ClientDto savedClientDto = clientInfoService.createClient(clientDto);
        return new ResponseEntity<>(savedClientDto, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ClientInfo> getClientByName(@PathVariable String name) {
        ClientInfo client;
        client = clientInfoRepository.findByClientName(name);
        if (client == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else
            return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PutMapping("/{clientName}")
    public ResponseEntity<ClientDto> updateClientByClientName(@PathVariable String clientName, @RequestBody ClientDto clientDto) {
        if (clientInfoRepository.findByClientName(clientName) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            ClientDto updatedClient = clientInfoService.updateClientByName(clientName, clientDto);
            return new ResponseEntity<>(updatedClient, HttpStatus.OK);
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> deleteClient(@PathVariable String name) {
        ClientInfo deletedClient = clientInfoService.deleteClient(name);
        if (deletedClient == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
