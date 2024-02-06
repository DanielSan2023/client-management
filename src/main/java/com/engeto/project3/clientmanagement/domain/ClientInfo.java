package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ClientInfo")
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
