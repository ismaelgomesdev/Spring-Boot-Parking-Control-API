package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ApartmentDto;
import com.api.parkingcontrol.dtos.VehicleDto;
import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.models.CondominiumModel;
import com.api.parkingcontrol.models.CondominiumResidentModel;
import com.api.parkingcontrol.models.VehicleModel;
import com.api.parkingcontrol.services.ApartmentService;
import com.api.parkingcontrol.services.CondominiumResidentService;
import com.api.parkingcontrol.services.VehicleService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/vehicle")
public class VehicleController {
    final VehicleService vehicleService;

    final CondominiumResidentService condominiumResidentService;

    final ApartmentService apartmentService;

    public VehicleController(VehicleService vehicleService, CondominiumResidentService condominiumResidentService, ApartmentService apartmentService) {
        this.vehicleService = vehicleService;
        this.condominiumResidentService = condominiumResidentService;
        this.apartmentService = apartmentService;
    }

    @PostMapping
    public ResponseEntity<Object> saveVehicle(@RequestBody @Valid VehicleDto vehicleDto){
        if(vehicleExists(vehicleDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Vehicle already exists!");
        }

        if(!this.condominiumResidentExists(vehicleDto))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium Resident not found.");

        if(!this.apartmentExists(vehicleDto))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Apartment not found.");

        Optional<ApartmentModel> apartmentModelOptional = apartmentService.findById(vehicleDto.getApartmentId());

        if(apartmentModelOptional.get().getParkingSpotQuantity() < 1)
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body("Not enough parking spaces");

        var vehicleModel = new VehicleModel();
        BeanUtils.copyProperties(vehicleDto, vehicleModel);
        vehicleModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        vehicleModel.setCondominiumResident(condominiumResidentService.findById(vehicleDto.getCondominiumResidentId()).get());
        vehicleModel.setApartment(apartmentModelOptional.get());

        apartmentModelOptional.get().setParkingSpotQuantity(apartmentModelOptional.get().getParkingSpotQuantity() - 1);

        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.save(vehicleModel));
    }

    @GetMapping
    public ResponseEntity<Page<VehicleModel>> getAllVehicles(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneVehicle(@PathVariable(value = "id") UUID id){
        Optional<VehicleModel> vehicleModelOptional = vehicleService.findById(id);
        if (!vehicleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(vehicleModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable(value = "id") UUID id){
        Optional<VehicleModel> vehicleModelOptional = vehicleService.findById(id);
        if (!vehicleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found.");
        }
        vehicleService.delete(vehicleModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Vehicle deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVehicle(@PathVariable(value = "id") UUID id,
                                                  @RequestBody @Valid VehicleDto vehicleDto){
        Optional<VehicleModel> vehicleModelOptional = vehicleService.findById(id);


        if (!vehicleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found.");
        }

        if(!this.condominiumResidentExists(vehicleDto))
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium Resident not found.");

        var vehicleModel = new VehicleModel();

        BeanUtils.copyProperties(vehicleDto, vehicleModel);
        vehicleModel.setId(vehicleModelOptional.get().getId());
        vehicleModel.setRegistrationDate(vehicleModelOptional.get().getRegistrationDate());
        vehicleModel.setCondominiumResident(condominiumResidentService.findById(vehicleDto.getCondominiumResidentId()).get());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.save(vehicleModel));
    }

    private boolean condominiumResidentExists(VehicleDto vehicleDto) {
        Optional<CondominiumResidentModel> condominiumResidentModelOptional = condominiumResidentService.findById(vehicleDto.getCondominiumResidentId());
        return condominiumResidentModelOptional.isPresent();
    }

    private boolean vehicleExists(VehicleDto vehicleDto) {
        return vehicleService.existsByLicensePlate(vehicleDto.getLicensePlate());
    }

    private boolean apartmentExists(VehicleDto vehicleDto) {
        return apartmentService.findById(vehicleDto.getApartmentId()).isPresent();
    }

}
