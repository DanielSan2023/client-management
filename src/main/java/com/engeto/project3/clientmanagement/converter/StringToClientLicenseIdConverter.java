package com.engeto.project3.clientmanagement.converter;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.springframework.core.convert.converter.Converter;

import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import org.springframework.stereotype.Component;

@Component
public class StringToClientLicenseIdConverter implements Converter<String, ClientLicenseId> {
    @Override
    public ClientLicenseId convert(String source) {

        String[] parts = source.split("-");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid format for ClientLicenseId: " + source);
        }

        try {
            Long clientInfoId = Long.parseLong(parts[0]);
            Long licenseId = Long.parseLong(parts[1]);

            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setId(clientInfoId);

            LicenseForSW license = new LicenseForSW();
            license.setId(licenseId);

            return new ClientLicenseId(clientInfo, license);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid format for ClientLicenseId: " + source, e);
        }
    }
}