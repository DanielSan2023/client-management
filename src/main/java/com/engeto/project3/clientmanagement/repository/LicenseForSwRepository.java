package com.engeto.project3.clientmanagement.repository;

import com.engeto.project3.clientmanagement.domain.LicenseForSW;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LicenseForSwRepository extends JpaRepository<LicenseForSW, Long> {

    void deleteByIdIn(List<Long> ids);

    void deleteById(Long Id);
}

