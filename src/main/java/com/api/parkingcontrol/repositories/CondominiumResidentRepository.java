package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.CondominiumModel;
import com.api.parkingcontrol.models.CondominiumResidentModel;
import com.api.parkingcontrol.models.ParkingSpotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CondominiumResidentRepository  extends JpaRepository<CondominiumResidentModel, UUID> {
    boolean existsByCpf(String cpf);
}
