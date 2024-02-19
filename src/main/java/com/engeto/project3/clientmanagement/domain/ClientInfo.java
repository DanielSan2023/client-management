package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CLIENT_INFO")
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

    @Column
    private String email;

    public ClientInfo(String clientName, String companyName, String address, String email) {
        this.clientName = clientName;
        this.companyName = companyName;
        this.address = address;
        this.email = email;
    }
}
