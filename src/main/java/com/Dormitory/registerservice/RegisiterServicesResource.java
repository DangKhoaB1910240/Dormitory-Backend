package com.Dormitory.registerservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/v1/register-services")
@CrossOrigin(origins = "http://localhost:4200")
public class RegisiterServicesResource {
    @Autowired
    private RegisterServicesService service;
    @PostMapping()
    public ResponseEntity<?> registerServices(@RequestBody @Valid RegisterServices registerServices) {
        service.registerServices(registerServices);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping("/{id}/update-status")
    public ResponseEntity<?> updateStatus(@PathVariable Integer id, @RequestParam Integer newStatus) {
        service.updateStatus(id,newStatus);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/service")
    public ResponseEntity<List<RegisterServicesDTO>> getAllRegisterService(@PathParam("sesmesterId") Integer sesmesterId, @PathParam("studentId") Integer studentId) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllRegisterService(sesmesterId,studentId));
    }
}
