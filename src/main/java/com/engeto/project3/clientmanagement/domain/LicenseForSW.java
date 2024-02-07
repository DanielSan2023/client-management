package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "LICENSE_FOR_SW")
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

    public LicenseForSW(String softwareName, String licenseKey) {
        this.softwareName = softwareName;
        this.licenseKey = licenseKey;
    }
}
