package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENT_INFO")
public class ClientInfo {
    transient EntityManager entityManager;

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

    public ClientInfo(String clientName, String companyName, String address) {
        this.clientName = clientName;
        this.companyName = companyName;
        this.address = address;
    }

}
