package com.Dormitory.registerservice;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Dormitory.contract.Contract;
import com.Dormitory.contract.ContractRepository;
import com.Dormitory.contract.ContractResponseDTO;
import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.service.Services;
import com.Dormitory.service.ServicesRepository;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

@Service
public class RegisterServicesService {
    private RegisterServicesRepository registerServicesRepository;
    private StudentRepository studentRepository;
    private SesmesterRepository sesmesterRepository;
    private ContractRepository contractRepository;
    private ServicesRepository servicesRepository;
    @Autowired
    public RegisterServicesService(RegisterServicesRepository registerServicesRepository,
            StudentRepository studentRepository, SesmesterRepository sesmesterRepository,
            ContractRepository contractRepository,ServicesRepository servicesRepository) {
        this.registerServicesRepository = registerServicesRepository;
        this.studentRepository = studentRepository;
        this.sesmesterRepository = sesmesterRepository;
        this.contractRepository = contractRepository;
        this.servicesRepository = servicesRepository;
    }
    public List<RegisterServicesDTO> getAllRegisterService(Integer sesmesterId, Integer studentId) {
        List<RegisterServices> registerServices = registerServicesRepository.findBySesmesterIdAndStudentId(sesmesterId, studentId);
        List<RegisterServicesDTO> registerServicesDTOs = new ArrayList<>();
        for(RegisterServices r : registerServices) {
            RegisterServicesDTO registerServicesDTO = new RegisterServicesDTO();
            Services services = servicesRepository.findById(r.getService().getId())
            .orElseThrow(() -> new NotFoundException("Không tồn tại dịch vụ này")); 
            registerServicesDTO.setId(r.getId());
            registerServicesDTO.setPrice(r.getPrice());
            registerServicesDTO.setName(services.getName());
            registerServicesDTO.setStatus(r.getStatus());
            registerServicesDTO.setMotorbikeLicensePlate(r.getMotorbikeLicensePlate());
            registerServicesDTOs.add(registerServicesDTO);
        }
        return registerServicesDTOs;
    }
    public void updateStatus(Integer id, Integer newStatus) {
        RegisterServices registerServices = registerServicesRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Không tại hồ sơ đăng ký dịch vụ này"));
        registerServices.setStatus(newStatus);
        registerServicesRepository.save(registerServices);
    }
    public void registerServices(RegisterServices registerServices) {
        Student student = studentRepository.findById(registerServices.getStudent().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại student này"));
        Services service =servicesRepository.findById(registerServices.getService().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại dịch vụ này"));
        Sesmester sesmester = sesmesterRepository.findSesmesterByCurrentDate(registerServices.getRegistrationDate())
        .orElseThrow(() -> new NotFoundException("Vui lòng quay lại thời gian đăng ký phù hợp"));
        Contract contract = contractRepository.findBySesmesterIdAndStudentId(sesmester.getId(), student.getId())
        .orElseThrow(() -> new NotFoundException("Sinh viên chưa đăng ký phòng ở nên không đăng ký được dịch vụ"));
        registerServices.setSesmester(sesmester);
        if(contract.getStatus() == 2) {
            throw new InvalidValueException("Bạn không đăng ký dịch vụ được vì đang trong danh sách đen");
        }
        if(registerServicesRepository.findBySesmesterIdAndStudentIdAndServiceId(sesmester.getId(), student.getId(), service.getId()).isPresent()){
            throw new AlreadyExistsException("Dịch vụ đã được đăng ký trong học kỳ này");
        }
        //Phần xử lý tính tổng giá nguyên học kì 
        LocalDate startDate = sesmester.getStartDate();
        LocalDate endDate = sesmester.getEndDate();
        int holidayWeek = sesmester.getHolidayWeek();
        // Tính số tuần giữa startDate và endDate
        int weeks = (int)startDate.until(endDate, java.time.temporal.ChronoUnit.WEEKS);
        Float weeklyPrice = service.getPrice()/4;
        registerServices.setPrice(weeklyPrice*weeks - holidayWeek*weeklyPrice);
        if (service.getName().equals("Gửi xe máy")) {
            if (registerServices.getMotorbikeLicensePlate() == null || registerServices.getMotorbikeLicensePlate().isEmpty()) {
                throw new InvalidValueException("Vui lòng nhập bản số xe máy");
            }
            // Lưu thông tin bản số xe máy vào đối tượng RegisterServices
            registerServices.setMotorbikeLicensePlate(registerServices.getMotorbikeLicensePlate());
        }

        registerServicesRepository.save(registerServices);
    }
    public Page<RegisterServices> getRegisterFromFilter(
        Integer sesmester,
        String schoolYear,
        String major,
        String numberStudent,
        Integer gender,
        Pageable pageable
    ) { 
        // Sort sort = Sort.by(Sort.Order.asc("roomType"), Sort.Order.asc("numberRoom"));
        
        Page<RegisterServices> registerPage = registerServicesRepository.findByFilter(sesmester, schoolYear, major, numberStudent, gender, pageable);
        return new PageImpl<>(registerPage.getContent(), pageable, registerPage.getTotalElements());
    }
    public Page<RegisterServices> getRegisterFromFilter(String search, Pageable pageable) {
        Page<RegisterServices> registerPage = registerServicesRepository.searchByStudentNameOrNumber(search, search, pageable);
        return new PageImpl<>(registerPage.getContent(), pageable, registerPage.getTotalElements());
    }
}
