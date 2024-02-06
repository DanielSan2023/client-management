package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Client_Info")
public class ClientInfo {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column
    private String clientName;

    @Column
    private String companyName;

    @Column
    private String address;

}
