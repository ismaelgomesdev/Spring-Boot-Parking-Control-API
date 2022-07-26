package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.CondominiumDto;
import com.api.parkingcontrol.dtos.CondominiumResidentDto;
import com.api.parkingcontrol.models.CondominiumModel;
import com.api.parkingcontrol.models.CondominiumResidentModel;
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
@RequestMapping("/condominium-resident")
public class ControllerResidentController {

    final CondominiumResidentService condominiumResidentService;

    public ControllerResidentController(CondominiumResidentService condominiumResidentService) {
        this.condominiumResidentService = condominiumResidentService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCondominiumResident(@RequestBody @Valid CondominiumResidentDto condominiumResidentDto){
        if(condominiumResidentExists(condominiumResidentDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Condominium Resident already exists!");
        }
        var condominiumResidentModel = new CondominiumResidentModel();
        BeanUtils.copyProperties(condominiumResidentDto, condominiumResidentModel);
        condominiumResidentModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(condominiumResidentService.save(condominiumResidentModel));
    }

    @GetMapping
    public ResponseEntity<Page<CondominiumResidentModel>> getAllCondominiumResidents(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(condominiumResidentService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCondominiumResident(@PathVariable(value = "id") UUID id){
        Optional<CondominiumResidentModel> condominiumModelOptional = condominiumResidentService.findById(id);
        if (!condominiumModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium Resident not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(condominiumModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCondominiumResident(@PathVariable(value = "id") UUID id){
        Optional<CondominiumResidentModel> condominiumModelOptional = condominiumResidentService.findById(id);
        if (!condominiumModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium Resident not found.");
        }
        condominiumResidentService.delete(condominiumModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Condominium Resident deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCondominiumResident(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid CondominiumResidentDto condominiumResidentDto){
        Optional<CondominiumResidentModel> condominiumResidentModelOptional = condominiumResidentService.findById(id);

        if (!condominiumResidentModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium Resident not found.");
        }
        var condominiumResidentModel = new CondominiumResidentModel();
        BeanUtils.copyProperties(condominiumResidentDto, condominiumResidentModel);
        condominiumResidentModel.setId(condominiumResidentModelOptional.get().getId());
        condominiumResidentModel.setRegistrationDate(condominiumResidentModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(condominiumResidentService.save(condominiumResidentModel));
    }

    private boolean condominiumResidentExists(CondominiumResidentDto condominiumResidentDto) {
        return condominiumResidentService.existsByCpf(condominiumResidentDto.getCpf());
    }

}
