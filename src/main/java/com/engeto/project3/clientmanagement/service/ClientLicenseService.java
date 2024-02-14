package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import com.engeto.project3.clientmanagement.dto.ClientLicenseDto;
import com.engeto.project3.clientmanagement.repository.AESEncryptionDecryption;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import com.engeto.project3.clientmanagement.repository.LicenseForSwRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientLicenseService {
    final private ClientLicenseRepository clientLicenseRepository;
    final private ClientInfoRepository clientInfoRepository;
    final private LicenseForSwRepository licenseRepository;
    private AESEncryptionDecryption encryptDecrypt;

    @PersistenceContext
    private EntityManager entityManager;

    public ClientLicenseService(ClientLicenseRepository clientLicenseRepository, ClientInfoRepository clientInfoRepository, LicenseForSwRepository licenseRepository, AESEncryptionDecryption encryptDecrypt) {
        this.clientLicenseRepository = clientLicenseRepository;
        this.clientInfoRepository = clientInfoRepository;
        this.licenseRepository = licenseRepository;
        this.encryptDecrypt = encryptDecrypt;
    }

    public ClientLicenseDto getClientLicenseById(ClientLicenseId id) {
        ClientLicense clientLicense = clientLicenseRepository.findByClientlicenseId(id);
        if (clientLicense == null) {
            throw new EntityNotFoundException("Client with Id ( " + id + ") not found.");
        }
        return mapClientLicenseToDto(clientLicense);
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
                .map(ClientLicenseService::mapClientLicenseToDto)
                .collect(Collectors.toList());
        return clientsLicensesDto;
    }

    @Transactional
    public ClientLicenseDto createLicense(ClientLicenseDto clientLicenseDto) {
        validationClientLicenseDto(clientLicenseDto);
        System.out.println("clientName :" + clientLicenseDto.getName());
        ClientInfo validatedClient = validationClient(clientLicenseDto.getName());


        LicenseForSW licenseKeyForSw = createLicensekeyForSw(clientLicenseDto.getName(), clientLicenseDto.getSoftwareName());
        entityManager.persist(licenseKeyForSw);

        ClientLicense newLicense = new ClientLicense(new ClientLicenseId(validatedClient, licenseKeyForSw), LocalDateTime.now());
        clientLicenseRepository.save(newLicense);
        return mapClientLicenseToDto(newLicense);
    }

    private LicenseForSW createLicensekeyForSw(String clientName, String softwareName) {
        LicenseForSW licenseKey = new LicenseForSW();
        licenseKey.setSoftwareName(softwareName);
        String originalString = clientName + softwareName + LocalDate.now();
        licenseKey.setLicenseKey(generateEncryptedKey(originalString));
        return licenseKey;
    }

    private String generateEncryptedKey(String originalString) {
        String secretKey = "secrete";
        return encryptDecrypt.encrypt(originalString, secretKey);
    }

    private void validationClientLicenseDto(ClientLicenseDto clientLicense) {
        String clientName = clientLicense.getName();
        String swName = clientLicense.getSoftwareName();
        ClientLicense existLicense = clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(clientName, swName);
        if (existLicense != null) {
            throw new RuntimeException("License is already exist in DB!");
        }
    }

    public ClientInfo validationClient(String name) {
        ClientInfo existClient = clientInfoRepository.findByClientName(name);
        if (existClient == null) {
            throw new EntityNotFoundException("Client " + name + " does not exist in database. You have to create client account!");
        } else {
            return existClient;
        }
    }

    private static ClientLicenseDto mapClientLicenseToDto(ClientLicense existLicense) {
        ClientLicenseDto clientLicenseDto = new ClientLicenseDto();
        clientLicenseDto.setName(existLicense.getClientlicenseId().getClient().getClientName());
        clientLicenseDto.setSoftwareName(existLicense.getClientlicenseId().getLicense().getSoftwareName());
        clientLicenseDto.setLicenseKey(existLicense.getClientlicenseId().getLicense().getLicenseKey());
        clientLicenseDto.setActive(checkExpiration(existLicense));
        return clientLicenseDto;
    }

    @Transactional
    public void deleteClientLicenseByNameAndSwName(String clientName, String swName) {
        ClientLicense clientLicense = clientLicenseRepository.findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(clientName, swName);
        Long licenseId = clientLicense.getClientlicenseId().getLicense().getId();
        clientLicenseRepository.deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(clientName, swName);
        licenseRepository.deleteById(licenseId);
    }
    public List<ClientInfo> getClientsToSendEmail() {
        // Implementácia získania klientov, ktorým treba poslať email
        // Napríklad z databázy alebo iného úložiska
        return null;
    }
}
