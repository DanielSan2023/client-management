package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.domain.ClientInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ClientInfoRepository extends JpaRepository<ClientInfo, Long> {

    ClientInfo findByClientName(String name);

    boolean existsByClientName(String name);
}
