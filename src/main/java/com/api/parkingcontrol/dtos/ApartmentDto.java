package com.api.parkingcontrol.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class ApartmentDto {
    @NotBlank
    private String block;

    @NotBlank
    private String apartmentNumber;

    @NotBlank
    private int parkingSpotQuantity;

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
}
