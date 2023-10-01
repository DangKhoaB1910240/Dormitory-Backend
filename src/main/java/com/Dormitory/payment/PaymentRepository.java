package com.Dormitory.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dormitory.contract.Contract;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
    boolean existsByContract(Contract contract);
}
