package com.Dormitory.registerservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.Dormitory.contract.ContractResponseDTO;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("api/v1/register-services")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
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
    @GetMapping
    public ResponseEntity<Page<RegisterServices>> getRegisterFromFilter(
        @RequestParam(required = false) Integer sesmester,
        @RequestParam(required = false) String schoolYear,
        @RequestParam(required = false) String major,
        @RequestParam(required = false) String numberStudent,
        @RequestParam(required = false) Integer gender,
        @PageableDefault(size = 6) Pageable pageable
    ) {
        
        return 
        ResponseEntity.status(HttpStatus.OK).body(service.getRegisterFromFilter(sesmester,schoolYear,major,numberStudent,gender,pageable));
    }
    @GetMapping("search")
    public ResponseEntity<Page<RegisterServices>> getFilterFromSearchFilter(
        @RequestParam(required = false) String search,
        @PageableDefault(size = 6) Pageable pageable
    ) {
        return 
        ResponseEntity.status(HttpStatus.OK).body(service.getRegisterFromFilter(search,pageable));
    }
}
