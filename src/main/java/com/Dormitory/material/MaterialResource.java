package com.Dormitory.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/material")
public class MaterialResource {
    @Autowired
    private MaterialService materialService;
    
    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterial() {
        return
        ResponseEntity.status(HttpStatus.OK).body(materialService.getAllMaterial());
    }
}
