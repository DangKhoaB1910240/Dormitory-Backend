package com.Dormitory.contract;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.admin.Admin;
import com.Dormitory.admin.AdminRepository;
import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.exception.sesmester.SesmesterDateValidationException;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

@Service
public class ContractService {
    private ContractRepository contractRepository;
    private StudentRepository studentRepository;
    private AdminRepository adminRepository;
    private SesmesterRepository sesmesterRepository;
    @Autowired
    public ContractService(ContractRepository contractRepository, StudentRepository studentRepository,
            AdminRepository adminRepository, SesmesterRepository sesmesterRepository) {
        this.contractRepository = contractRepository;
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
        this.sesmesterRepository = sesmesterRepository;
    }

    public void addContract(Contract contract) {
        studentRepository.findById(contract.getStudent().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên với id: "+contract.getStudent().getId()));
        adminRepository.findById(contract.getAdmin().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại admin với id: "+contract.getAdmin().getId()));
        sesmesterRepository.findById(contract.getSesmester().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại học kỳ với id: "+contract.getSesmester().getId()));
        sesmesterRepository.findByIdAndStatus(contract.getSesmester().getId(), true).orElseThrow(() -> new SesmesterDateValidationException("Học kỳ đang đóng"));
        contractRepository.findBySesmesterIdAndStudentId(contract.getSesmester().getId(),contract.getStudent().getId()).orElseThrow(() -> new AlreadyExistsException("Sinh viên này đã đăng ký học kỳ này rồi"));
        // Lưu hợp đồng vào CSDL

        contractRepository.save(contract);
    }
    
}
