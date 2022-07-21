package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ApartmentDto;
import com.api.parkingcontrol.dtos.VehicleDto;
import com.api.parkingcontrol.models.ApartmentModel;
import com.api.parkingcontrol.models.VehicleModel;
import com.api.parkingcontrol.services.ApartmentService;
import com.api.parkingcontrol.services.VehicleService;
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
@RequestMapping("/vehicle")
public class VehicleController {
    final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping
    public ResponseEntity<Object> saveVehicle(@RequestBody @Valid VehicleDto vehicleDto){
        var vehicleModel = new VehicleModel();
        BeanUtils.copyProperties(vehicleDto, vehicleModel);
        vehicleModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(vehicleModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteVehicle(@PathVariable(value = "id") UUID id){
        Optional<VehicleModel> vehicleModelOptional = vehicleService.findById(id);
        if (!vehicleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        vehicleService.delete(vehicleModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateVehicle(@PathVariable(value = "id") UUID id,
                                                  @RequestBody @Valid VehicleDto vehicleDto){
        Optional<VehicleModel> vehicleModelOptional = vehicleService.findById(id);
        if (!vehicleModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        var vehicleModel = new VehicleModel();
        BeanUtils.copyProperties(vehicleDto, vehicleModel);
        vehicleModel.setId(vehicleModelOptional.get().getId());
        vehicleModel.setRegistrationDate(vehicleModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.save(vehicleModel));
    }
}
