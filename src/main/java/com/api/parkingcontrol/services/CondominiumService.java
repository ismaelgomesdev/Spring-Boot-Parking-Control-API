package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.CondominiumModel;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.CondominiumRepository;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
@Service
public class CondominiumService {
    final CondominiumRepository condominiumRepository;

    public CondominiumService(com.api.parkingcontrol.repositories.CondominiumRepository condominiumRepository) {
        this.condominiumRepository = condominiumRepository;
    }

    @Transactional
    public CondominiumModel save(CondominiumModel condominiumModel) {
        return condominiumRepository.save(condominiumModel);
    }

    public Page<CondominiumModel> findAll(Pageable pageable) {
        return condominiumRepository.findAll(pageable);
    }

    public Optional<CondominiumModel> findById(UUID id) {
        return condominiumRepository.findById(id);
    }

    @Transactional
    public void delete(CondominiumModel condominiumModel) {
        condominiumRepository.delete(condominiumModel);
    }
}
