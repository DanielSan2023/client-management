package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class LicenseForSW {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String softwareName;

    @Column
    private String licenseKey;
}
