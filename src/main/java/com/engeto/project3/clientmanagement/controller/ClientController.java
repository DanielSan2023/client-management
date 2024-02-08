package com.engeto.project3.clientmanagement.controller;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {
    final private ClientInfoRepository clientInfoRepository;

    public ClientController(ClientInfoRepository clientInfoRepository) {
        this.clientInfoRepository = clientInfoRepository;
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
}
