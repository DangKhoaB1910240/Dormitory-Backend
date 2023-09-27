package com.Dormitory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/service")
@CrossOrigin("http://localhost:4200")
public class ServicesResource {
    @Autowired
    private ServicesService service;

    @GetMapping()
    public ResponseEntity<List<Services>> getAllServices() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllService());
    }
}
