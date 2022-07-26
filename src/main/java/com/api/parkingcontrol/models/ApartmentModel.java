package com.api.parkingcontrol.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

@Entity
@Table(name = "TB_APARTMENT")
public class ApartmentModel {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 30)
    private String block;

    @Column(nullable = false, length = 30)
    private String apartmentNumber;

    @Column(nullable = false, length = 10)
    private int parkingSpotQuantity;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_condominium_resident")
    private CondominiumResidentModel condominiumResident;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "fk_id_condominium")
    private CondominiumModel condominium;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public int getParkingSpotQuantity() {
        return parkingSpotQuantity;
    }

    public void setParkingSpotQuantity(int parkingSpotQuantity) {
        this.parkingSpotQuantity = parkingSpotQuantity;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public CondominiumResidentModel getCondominiumResident() {
        return condominiumResident;
    }

    public void setCondominiumResident(CondominiumResidentModel condominiumResident) {
        this.condominiumResident = condominiumResident;
    }

    public CondominiumModel getCondominium() {
        return condominium;
    }

    public void setCondominium(CondominiumModel condominium) {
        this.condominium = condominium;
    }
}
