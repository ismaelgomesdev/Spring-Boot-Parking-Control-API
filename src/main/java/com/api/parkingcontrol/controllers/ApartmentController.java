package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ApartmentDto;
import com.api.parkingcontrol.dtos.CondominiumDto;
import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.models.CondominiumModel;
import com.api.parkingcontrol.models.CondominiumResidentModel;
import com.api.parkingcontrol.services.ApartmentService;
import com.api.parkingcontrol.services.CondominiumResidentService;
import com.api.parkingcontrol.services.CondominiumService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/apartment")
public class ApartmentController {

    final ApartmentService apartmentService;
    final CondominiumService condominiumService;
    final CondominiumResidentService condominiumResidentService;

    public ApartmentController(ApartmentService apartmentService, CondominiumService condominiumService, CondominiumResidentService condominiumResidentService) {
        this.apartmentService = apartmentService;
        this.condominiumService = condominiumService;
        this.condominiumResidentService = condominiumResidentService;
    }

    @PostMapping
    public ResponseEntity<Object> saveApartment(@RequestBody @Valid ApartmentDto apartmentDto){
        var apartmentModel = new ApartmentModel();
        BeanUtils.copyProperties(apartmentDto, apartmentModel);
        apartmentModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        Optional<CondominiumModel> condominiumModelOptional = condominiumService.findById(apartmentDto.getCondominiumId());
        Optional<CondominiumResidentModel> condominiumResidentModelOptional = condominiumResidentService.findById(apartmentDto.getCondominiumResidentId());
        if (!condominiumModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium not found.");
        }
        if(!condominiumResidentModelOptional.isPresent())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium Resident not found.");

        apartmentModel.setCondominium(condominiumModelOptional.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(apartmentService.save(apartmentModel));
    }

    @GetMapping
    public ResponseEntity<Page<ApartmentModel>> getAllApartments(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneApartment(@PathVariable(value = "id") UUID id){
        Optional<ApartmentModel> apartmentModelOptional = apartmentService.findById(id);
        if (!apartmentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(apartmentModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteApartment(@PathVariable(value = "id") UUID id){
        Optional<ApartmentModel> apartmentModelOptional = apartmentService.findById(id);
        if (!apartmentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found.");
        }
        apartmentService.delete(apartmentModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Apartment deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateApartment(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid ApartmentDto apartmentDto){
        Optional<ApartmentModel> apartmentModelOptional = apartmentService.findById(id);
        if (!apartmentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found.");
        }
        var apartmentModel = new ApartmentModel();
        BeanUtils.copyProperties(apartmentDto, apartmentModel);
        apartmentModel.setId(apartmentModelOptional.get().getId());
        apartmentModel.setRegistrationDate(apartmentModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(apartmentService.save(apartmentModel));
    }

    private boolean apartmentExists(ApartmentDto apartmentDto) {
        //Optional<ApartmentModel> apartmentModelOptional = apartmentService.fin

        return false;
    }

}
