package com.api.parkingcontrol.dtos;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

public class CondominiumResidentDto {
    @NotBlank
    private String name;

    @NotBlank
    private String cpf;

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
}
