package com.engeto.project3.clientmanagement.config.email;

import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.repository.ClientLicenseRepository;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@StepScope
@Component
public class LicenseReader implements ItemReader<ClientLicense> {

    private final Iterator<ClientLicense> licenseIterator;

    @Autowired
    public LicenseReader(ClientLicenseRepository clientLicenseRepository) {
        List<ClientLicense> licenses = clientLicenseRepository.findAll();
        this.licenseIterator = licenses.iterator();
    }

    @Override
    public ClientLicense read() throws Exception {
        if (licenseIterator != null && licenseIterator.hasNext()) {
            return licenseIterator.next();
        } else {
            return null;
        }
    }
}