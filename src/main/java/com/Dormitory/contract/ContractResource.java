package com.Dormitory.contract;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.Dormitory.service.Services;
import com.Dormitory.student.Student;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/contract")
public class ContractResource {
    @Autowired
    private ContractService contractService;

    @GetMapping("student/{studentId}/sesmester/{sesmesterId}")
    public ResponseEntity<Contract> getContract(@PathVariable("studentId") Integer studentId,@PathVariable("sesmesterId") Integer sesmesterId ) {
        Contract contract = contractService.getContract(studentId,sesmesterId);
        return ResponseEntity.ok().body(contract);
    }

    @GetMapping
    public ResponseEntity<List<ContractResponseDTO>> getContractFromFilter(
        @RequestParam(required = false) Integer sesmester,
        @RequestParam(required = false) String schoolYear,
        @RequestParam(required = false) String major,
        @RequestParam(required = false) String numberStudent,
        @RequestParam(required = false) Integer gender
    ) {
        
        return 
        ResponseEntity.status(HttpStatus.OK).body(contractService.getContractFromFilter(sesmester,schoolYear,major,numberStudent,gender));
    }

    @PostMapping()
    public ResponseEntity<Void> addContract(@Valid @RequestBody Contract contract) {
        contractService.addContract(contract);
        return ResponseEntity.ok().build();
    }
    @GetMapping("student/roomtype/{roomtypeId}/room/{numberRoom}")
    public ResponseEntity<List<Student>> getAllStudentsFromContract(@PathVariable("roomtypeId") Integer roomTypeId, @PathVariable("numberRoom") Integer numberRoom) {
        List<Student> students = contractService.getAllStudentsFromContract(roomTypeId,numberRoom);
        return ResponseEntity.status(HttpStatus.OK).body(students);
    }
    // @PatchMapping("/register-service/{id}")
    // public ResponseEntity<Void> registerServices(@PathVariable Integer id,@RequestBody List<Services> services) {
    //     contractService.registerServices(id,services);
    //     return ResponseEntity.noContent().build();
    // }
}
