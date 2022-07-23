package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.ApartmentModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ApartmentRepository extends JpaRepository<ApartmentModel, UUID> {

}
