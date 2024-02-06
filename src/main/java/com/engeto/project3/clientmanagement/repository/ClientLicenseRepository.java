package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClientLicenseRepository extends JpaRepository<ClientLicense, ClientLicenseId> {

    //ClientInfo findByClientLicenseIdClientId(Long clientId);

    ClientLicense findClientLicenseById(ClientLicenseId id);
}
