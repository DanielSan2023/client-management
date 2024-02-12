package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.domain.ClientLicense;
import com.engeto.project3.clientmanagement.domain.ClientLicenseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public interface ClientLicenseRepository extends JpaRepository<ClientLicense, ClientLicenseId> {

    ClientLicense findByClientlicenseId(ClientLicenseId id);

    ClientLicense findByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(String clientName, String softwareName);

    @Transactional
    void deleteByClientlicenseId_Client_ClientNameAndClientlicenseId_License_SoftwareName(String clientName, String softwareName);

    @Transactional
    void deleteAllByClientlicenseId_Client_ClientName(String name);

    @Query("SELECT cl.clientlicenseId.license.id FROM ClientLicense cl " +
            "WHERE cl.clientlicenseId.client.id = (SELECT c.id FROM ClientInfo c WHERE c.clientName = :clientName)")
    List<Long> findAllLicenseIdByClientName(@Param("clientName") String clientName);
}






