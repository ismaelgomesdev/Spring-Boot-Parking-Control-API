package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.CondominiumModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CondominiumRepository extends JpaRepository<CondominiumModel, UUID> {
}
