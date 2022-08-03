package com.api.parkingcontrol.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class CondominiumDto {

    @NotBlank
    private String cnpj;

    @NotBlank
    private String address;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
