package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENT_LICENSE")
public class ClientLicense {

    @EmbeddedId
    private ClientLicenseId clientlicenseId;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

}
