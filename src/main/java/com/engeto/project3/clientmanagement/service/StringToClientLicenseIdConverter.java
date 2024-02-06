package com.engeto.project3.clientmanagement.service;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;

@Component
public class StringToClientLicenseIdConverter implements Converter<String, ClientLicenseId> {

    @Override
    public ClientLicenseId convert(String source) {
        // Implement logic to convert the string to ClientLicenseId
        // Example logic assuming format is "clientId-licenseId"
        String[] parts = source.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format for ClientLicenseId");
        }
        Long clientId = Long.parseLong(parts[0]);
        Long licenseId = Long.parseLong(parts[1]);
        ClientInfo clientInfo = new ClientInfo();
        clientInfo.setId(clientId);
        LicenseForSW licenseForSW = new LicenseForSW();
        licenseForSW.setId(licenseId);
        return new ClientLicenseId(clientInfo, licenseForSW);
    }
}
