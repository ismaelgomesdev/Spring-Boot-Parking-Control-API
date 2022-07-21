package com.api.parkingcontrol.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class VehicleDto {
    @NotBlank
    @Size(max = 7)
    private String licensePlate;

    @NotBlank
    private String brand;

    @NotBlank
    private String model;

    @NotBlank
    private String color;

    @NotBlank
    private String year;

    @NotBlank
    private String spotAdress;

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSpotAdress() {
        return spotAdress;
    }

    public void setSpotAdress(String spotAdress) {
        this.spotAdress = spotAdress;
    }
}
