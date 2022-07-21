package com.api.parkingcontrol.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

public class CondominiumResidentModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, unique = true, length = 18)
    private String cpf;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @OneToMany(mappedBy = "VehicleModel")
    private Collection<VehicleModel> vehicles;

    @OneToMany(mappedBy = "ApartmentModel")
    private Collection<ApartmentModel> apartments;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }
}
