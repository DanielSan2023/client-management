package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLicenseService {
    final private ClientLicenseRepository clientLicenseRepository;

    public ClientLicenseService(ClientLicenseRepository clientLicenseRepository) {
        this.clientLicenseRepository = clientLicenseRepository;
    }

    public ClientLicenseDto getClientLicenseById(ClientLicenseId id) {
        ClientLicense clientLicense = findById(id);
        if (clientLicense == null) {
            throw new EntityNotFoundException("Client with Id ( " + id + ") not found.");
        }
        return convertToDto(clientLicense);
    }

    private static ClientLicenseDto convertToDto(ClientLicense client) {
        ClientLicenseDto clientLicenseDto = new ClientLicenseDto();
        clientLicenseDto.setName(client.getId().getClient().getClientName());
        clientLicenseDto.setSoftwareName(client.getId().getLicense().getSoftwareName());
        clientLicenseDto.setLicenseKey(client.getId().getLicense().getLicenseKey());
        clientLicenseDto.setActive(checkExpiration(client));
        return clientLicenseDto;
    }

    public static boolean checkExpiration(ClientLicense client) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime startDate = client.getStartDate();
        long monthsDifference = ChronoUnit.MONTHS.between(startDate, currentDateTime);
        boolean isActive = monthsDifference <= 2;

        return isActive;
    }


    private ClientLicense findById(ClientLicenseId id) {
        return clientLicenseRepository.findClientLicenseById(id);
    }

    public List<ClientLicense> getAllClient() {
        //        List<ClientLicense> clientsLicensesDto = clientLicensesList.stream()
//                .map(ClientLicenseService::convertToDto)
//                .collect(Collectors.toList());
        return clientLicenseRepository.findAll();
    }

}
