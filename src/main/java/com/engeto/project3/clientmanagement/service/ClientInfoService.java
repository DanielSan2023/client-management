package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.dto.ClientDto;
import com.engeto.project3.clientmanagement.repository.ClientInfoRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ClientInfoService {
    final private ClientInfoRepository clientInfoRepository;
    private ModelMapper modelMapper;

    public ClientDto createClient(ClientDto clientDto) {
        validateNewClient(clientDto);
        ClientInfo newClientInfo = convertToDto(clientDto);
        clientInfoRepository.save(newClientInfo);
        return clientDto;
    }

    private void validateNewClient(ClientDto clientDto) {
        String newClientName = clientDto.getClientName();
        boolean isExistClient = clientInfoRepository.existsByClientName(newClientName);
        if (isExistClient) {
            throw new RuntimeException("Client  " + newClientName + " is already exist in DB!");
        }
    }

    private ClientInfo convertToDto(ClientDto clientDto) {
        return modelMapper.map(clientDto, ClientInfo.class);
    }
}
