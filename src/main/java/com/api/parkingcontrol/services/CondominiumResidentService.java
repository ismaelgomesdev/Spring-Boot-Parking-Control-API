package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.models.CondominiumResidentModel;
import com.api.parkingcontrol.repositories.ApartmentRepository;
import com.api.parkingcontrol.repositories.CondominiumResidentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
public class CondominiumResidentService {
    final CondominiumResidentRepository condominiumResidentRepository;

    public CondominiumResidentService(CondominiumResidentRepository condominiumResidentRepository) {
        this.condominiumResidentRepository = condominiumResidentRepository;
    }

    public boolean existsByCpf(String cpf) {
        return condominiumResidentRepository.existsByCpf(cpf);
    }

    @Transactional
    public CondominiumResidentModel save(CondominiumResidentModel condominiumResidentModel) {
        return condominiumResidentRepository.save(condominiumResidentModel);
    }

    public Page<CondominiumResidentModel> findAll(Pageable pageable) {
        return condominiumResidentRepository.findAll(pageable);
    }

    public Optional<CondominiumResidentModel> findById(UUID id) {
        return condominiumResidentRepository.findById(id);
    }

    @Transactional
    public void delete(CondominiumResidentModel condominiumResidentModel) {
        condominiumResidentRepository.delete(condominiumResidentModel);
    }
}
