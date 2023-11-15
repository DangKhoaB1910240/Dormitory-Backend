package com.Dormitory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/service")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
public class ServicesResource {
    @Autowired
    private ServicesService service;

    @GetMapping()
    public ResponseEntity<List<Services>> getAllServices() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllService());
    }
    
    @GetMapping("{id}")
    public ResponseEntity<Services> getServiceById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getServiceById(id));
    }
    @PutMapping()
    public ResponseEntity<Void> updateService(@RequestBody Services services) {
        service.updateService(services);
        return ResponseEntity.noContent().build();
    }
    @PostMapping()
    public ResponseEntity<Void> createService(@Valid @RequestBody Services services) {
        service.createService(services);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
