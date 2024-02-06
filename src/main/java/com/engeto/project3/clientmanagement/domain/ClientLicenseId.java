package com.engeto.project3.clientmanagement.domain;

import jakarta.persistence.*;

import java.io.Serializable;

@Embeddable
public class ClientLicenseId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "ClientInfo_id")
    private ClientInfo client;


    @ManyToOne
    @JoinColumn(name = "License_id")
    private LicenseForSW license;
}
