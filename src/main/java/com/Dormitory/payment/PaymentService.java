package com.Dormitory.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.contract.Contract;
import com.Dormitory.contract.ContractRepository;
import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.NotFoundException;

@Service
public class PaymentService {
    private PaymentRepository paymentRepository;
    private ContractRepository contractRepository;
    @Autowired
    public PaymentService(PaymentRepository paymentRepository, ContractRepository contractRepository) {
        this.paymentRepository = paymentRepository;
        this.contractRepository = contractRepository;
    }

    public void addPayment(Payment payment) {
        Contract contract = contractRepository.findById(payment.getContract().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại hợp đồng với id: "+payment.getContract().getId()));
        if(paymentRepository.existsByContract(contract)) {
            throw new AlreadyExistsException("Tồn tại hợp đồng này trong thanh toán rồi");
        }
        payment.setTotalPrice(contract.getTotalPrice());
        paymentRepository.save(payment);
    }
}
