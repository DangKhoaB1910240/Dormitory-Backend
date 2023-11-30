package com.Dormitory.sesmester;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4401"})
@RequestMapping("api/v1/sesmester")
public class SesmesterResource {
    @Autowired
    private SesmesterService sesmesterService;
    
    @GetMapping("status/{status}")
    public ResponseEntity<Sesmester> getSesmesterByStatus(@PathVariable("status") Boolean status) {
        return ResponseEntity.status(HttpStatus.OK).body(sesmesterService.getSesmesterByStatus(status));
    }
    @GetMapping
    public ResponseEntity<List<Sesmester>> getAllSesmester() {
        return ResponseEntity.status(HttpStatus.OK).body(sesmesterService.getAllSesmester());
    }
    @PostMapping
    public ResponseEntity<Void> createSesmester(@RequestBody @Valid Sesmester sesmester) {
        sesmesterService.createSesmester(sesmester);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
