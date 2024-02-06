package com.engeto.project3.clientmanagement.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ClientLicenseDto {

    private String name;

    private String softwareName;

    private String licenseKey;

    public boolean isActive() {
        return false;
    }

}
