package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.models.VehicleModel;
import com.api.parkingcontrol.repositories.ApartmentRepository;
import com.api.parkingcontrol.repositories.VehicleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class VehicleService {

    final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Transactional
    public VehicleModel save(VehicleModel vehicleModel) {
        return vehicleRepository.save(vehicleModel);
    }

    public Page<VehicleModel> findAll(Pageable pageable) {
        return vehicleRepository.findAll(pageable);
    }

    public Optional<VehicleModel> findById(UUID id) {
        return vehicleRepository.findById(id);
    }

    @Transactional
    public void delete(VehicleModel vehicleModel) {
        vehicleRepository.delete(vehicleModel);
    }

}
