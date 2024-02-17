package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import com.engeto.project3.clientmanagement.repository.LicenseForSwRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ClientInfoService {
    final private ClientInfoRepository clientInfoRepository;
    final private ClientLicenseRepository clientLicenseRepository;
    final private LicenseForSwRepository licenseForSwRepository;

    private ModelMapper modelMapper;

    public ClientDto createClient(ClientDto clientDto) {
        validateNewClient(clientDto);
        ClientInfo newClientInfo = convertToDomain(clientDto);
        clientInfoRepository.save(newClientInfo);
        return clientDto;
    }

    public ClientDto getClientByName(String name) {
        ClientInfo savedClient = clientInfoRepository.findByClientName(name);
        return convertToDto(savedClient);
    }

    private void validateNewClient(ClientDto clientDto) {
        String newClientName = clientDto.getClientName();
        boolean isExistClient = clientInfoRepository.existsByClientName(newClientName);
        if (isExistClient) {
            throw new RuntimeException("Client  " + newClientName + " is already exist in DB!");
        }
    }

    private ClientInfo convertToDomain(ClientDto clientDto) {
        return modelMapper.map(clientDto, ClientInfo.class);
    }

    private ClientDto convertToDto(ClientInfo client) {
        return modelMapper.map(client, ClientDto.class);
    }

    public ClientDto updateClientByName(String clientName, ClientDto clientDto) {
        ClientInfo client = clientInfoRepository.findByClientName(clientName);
        client.setCompanyName(clientDto.getCompanyName());
        client.setAddress(clientDto.getAddress());
        client.setEmail(clientDto.getEmail());
        clientInfoRepository.save(client);
        return convertToDto(client);
    }

    @Transactional
    public ClientInfo deleteClient(String name) {
        List<Long> licenseIds = clientLicenseRepository.findAllLicenseIdByClientName(name);
        clientLicenseRepository.deleteAllByClientlicenseId_Client_ClientName(name);
        clientInfoRepository.deleteByClientName(name);
        licenseForSwRepository.deleteByIdIn(licenseIds);
        ClientInfo deletedClient = clientInfoRepository.findByClientName(name);
        return deletedClient;
    }
}
