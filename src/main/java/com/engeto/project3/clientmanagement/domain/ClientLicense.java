package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class ClientLicense {

    @EmbeddedId
    private ClientLicenseId id;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

}
