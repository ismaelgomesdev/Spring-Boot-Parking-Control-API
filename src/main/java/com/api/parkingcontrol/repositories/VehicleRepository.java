package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleModel, UUID> {
    boolean existsByLicensePlate(String licensePlate);
}
