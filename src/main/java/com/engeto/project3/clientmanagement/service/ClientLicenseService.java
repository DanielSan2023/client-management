package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLicenseService {
    final private ClientLicenseRepository clientLicenseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ClientLicenseService(ClientLicenseRepository clientLicenseRepository) {
        this.clientLicenseRepository = clientLicenseRepository;
    }

    public ClientLicenseDto getClientLicenseById(ClientLicenseId id) {
        ClientLicense clientLicense = clientLicenseRepository.findByClientlicenseId(id);
        if (clientLicense == null) {
            throw new EntityNotFoundException("Client with Id ( " + id + ") not found.");
        }
        return convertToDto(clientLicense);
    }

    private static ClientLicenseDto convertToDto(ClientLicense client) {
        ClientLicenseDto clientLicenseDto = new ClientLicenseDto();
        clientLicenseDto.setName(client.getClientlicenseId().getClient().getClientName());
        clientLicenseDto.setSoftwareName(client.getClientlicenseId().getLicense().getSoftwareName());
        clientLicenseDto.setLicenseKey(client.getClientlicenseId().getLicense().getLicenseKey());
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


    public List<ClientLicenseDto> getAllClient() {
        List<ClientLicense> clientsLicenses = clientLicenseRepository.findAll();
        List<ClientLicenseDto> clientsLicensesDto = clientsLicenses.stream()
                .map(ClientLicenseService::convertToDto)
                .collect(Collectors.toList());
        return clientsLicensesDto;
    }

    @Transactional
    public void saveClientLicense() {
        ClientLicense clientLicense;

        ClientInfo client = new ClientInfo("Jenny", "IBM", "LA");
        LicenseForSW license = new LicenseForSW("Windows", "someKey");
        entityManager.persist(client);
        entityManager.persist(license);

        clientLicense = (new ClientLicense(new ClientLicenseId(client, license), LocalDateTime.now()));
        clientLicenseRepository.save(clientLicense);
    }

}
