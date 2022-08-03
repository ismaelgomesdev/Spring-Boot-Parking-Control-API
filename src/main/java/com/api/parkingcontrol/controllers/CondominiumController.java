package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.CondominiumDto;
import com.api.parkingcontrol.models.CondominiumModel;
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
@RequestMapping("/condominium")
public class CondominiumController {

    final CondominiumService condominiumService;

    public CondominiumController(CondominiumService condominiumService) {
        this.condominiumService = condominiumService;
    }

    @PostMapping
    public ResponseEntity<Object> saveCondominium(@RequestBody @Valid CondominiumDto condominiumDto){
        if(condominiumExists(condominiumDto)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Condominium already exists!");
        }

        var condominiumModel = new CondominiumModel();
        BeanUtils.copyProperties(condominiumDto, condominiumModel);
        condominiumModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(condominiumService.save(condominiumModel));
    }

    @GetMapping
    public ResponseEntity<Page<CondominiumModel>> getAllCondominiums(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(condominiumService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCondominium(@PathVariable(value = "id") UUID id){
        Optional<CondominiumModel> condominiumModelOptional = condominiumService.findById(id);
        if (!condominiumModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(condominiumModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCondominium(@PathVariable(value = "id") UUID id){
        Optional<CondominiumModel> condominiumModelOptional = condominiumService.findById(id);
        if (!condominiumModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium not found.");
        }
        condominiumService.delete(condominiumModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Condominium deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCondominium(@PathVariable(value = "id") UUID id,
                                                    @RequestBody @Valid CondominiumDto condominiumDto){
        Optional<CondominiumModel> condominiumModelOptional = condominiumService.findById(id);


        if (!condominiumModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Condominium not found.");
        }
        var condominiumModel = new CondominiumModel();
        BeanUtils.copyProperties(condominiumDto, condominiumModel);
        condominiumModel.setId(condominiumModelOptional.get().getId());
        condominiumModel.setRegistrationDate(condominiumModelOptional.get().getRegistrationDate());
        return ResponseEntity.status(HttpStatus.OK).body(condominiumService.save(condominiumModel));
    }

    private boolean condominiumExists(CondominiumDto condominiumDto) {
        return condominiumService.existsByCnpj(condominiumDto.getCnpj());
    }

}
