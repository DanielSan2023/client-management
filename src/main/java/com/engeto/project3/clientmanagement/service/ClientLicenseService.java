package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLicenseService {
    final private ClientLicenseRepository clientLicenseRepository;
    private static ModelMapper modelMapper;

    public ClientLicenseService(ClientLicenseRepository clientLicenseRepository) {
        this.clientLicenseRepository = clientLicenseRepository;
    }

    public ClientLicenseDto getClientLicenseById(ClientLicenseId clientId) {
        ClientLicense clientLicense = findById(clientId);
        if (clientLicense == null) {
            throw new EntityNotFoundException("Client with Id ( " + clientId + ") not found.");
        }
        return convertToDto(clientLicense);
    }

    private static ClientLicenseDto convertToDto(ClientLicense client) {
        ClientLicenseDto clientLicenseDto = modelMapper.map(client, ClientLicenseDto.class);
        return clientLicenseDto;

    }

    private ClientLicense findById(ClientLicenseId clientId) {
        return clientLicenseRepository.findClientLicenseById(clientId);
    }

    public List<ClientLicenseDto> getAllClient() {
        List<ClientLicense> clientLicensesList = clientLicenseRepository.findAll();
        List<ClientLicenseDto> clientsLicensesDto = clientLicensesList.stream()
                .filter(clientLicense -> clientLicense.getStartDate()==null)
                .map(ClientLicenseService::convertToDto)
                .collect(Collectors.toList());
        return clientsLicensesDto;
    }


}
