package com.Dormitory.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Dormitory.service.Services;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/contract")
public class ContractResource {
    @Autowired
    private ContractService contractService;

    @PostMapping()
    public ResponseEntity<Void> addContract(@Valid @RequestBody Contract contract) {
        contractService.addContract(contract);
        return ResponseEntity.ok().build();
    }
    @PostMapping("/register-service/{id}")
    public ResponseEntity<Void> registerServices(@PathVariable Integer id,@RequestBody List<Services> services) {
        contractService.registerServices(id,services);
        return ResponseEntity.noContent().build();
    }
}