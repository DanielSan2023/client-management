package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ClientInfo")
public class ClientInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String companyName;

    @Column
    private String address;

}
