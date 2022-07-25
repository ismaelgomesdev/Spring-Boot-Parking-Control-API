package com.api.parkingcontrol.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ApartmentDto {
    @NotBlank
    private String block;

    @NotBlank
    private String apartmentNumber;

    @NotNull
    private int parkingSpotQuantity;

    @NotNull
    private UUID condominiumId;

    @NotNull
    private UUID condominiumResidentId;

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

    public UUID getCondominiumId() {
        return condominiumId;
    }

    public void setCondominiumId(UUID condominiumId) {
        this.condominiumId = condominiumId;
    }

    public UUID getCondominiumResidentId() {
        return condominiumResidentId;
    }

    public void setCondominiumResidentId(UUID condominiumResidentId) {
        this.condominiumResidentId = condominiumResidentId;
    }
}
