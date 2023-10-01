package com.Dormitory.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/payment")
public class PaymentResource {
    @Autowired
    private PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<Void> addPayment(@Valid @RequestBody Payment payment) {
        paymentService.addPayment(payment);
        return ResponseEntity.ok().build();
    }
}
