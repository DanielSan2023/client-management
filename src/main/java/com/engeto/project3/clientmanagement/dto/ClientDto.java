package com.engeto.project3.clientmanagement.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientDto {

    private Long id;

    @Size(max = 255)
    private String clientName;

    @Size(max = 255)
    private String companyName;

    @Size(max = 255)
    private String address;

    @Size(max = 255)
    private String email;

    public ClientDto(String clientName, String companyName, String address, String email) {
        this.clientName = clientName;
        this.companyName = companyName;
        this.address = address;
        this.email = email;
    }
}
