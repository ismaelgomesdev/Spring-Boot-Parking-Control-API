package com.api.parkingcontrol.services;

import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ApartmentRepository;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
@Service
public class ApartmentService {

    final ApartmentRepository apartmentRepository;

    public ApartmentService(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    @Transactional
    public ApartmentModel save(ApartmentModel apartmentModel) {
        return apartmentRepository.save(apartmentModel);
    }

    public Page<ApartmentModel> findAll(Pageable pageable) {
        return apartmentRepository.findAll(pageable);
    }

    public Optional<ApartmentModel> findById(UUID id) {
        return apartmentRepository.findById(id);
    }

    public boolean existsByApartmentAndBlock(String apartmentNumber, String block) {
        return apartmentRepository.existsByApartmentNumberAndBlock(apartmentNumber, block);
    }

    @Transactional
    public void delete(ApartmentModel apartmentModel) {
        apartmentRepository.delete(apartmentModel);
    }
}
