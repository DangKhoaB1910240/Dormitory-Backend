package com.Dormitory.contract;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.Dormitory.admin.Admin;
import com.Dormitory.admin.AdminRepository;
import com.Dormitory.email.Email;
import com.Dormitory.email.EmailService;
import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.exception.sesmester.SesmesterDateValidationException;
import com.Dormitory.room.Room;
import com.Dormitory.room.RoomRepository;
import com.Dormitory.roomtype.RoomType;
import com.Dormitory.roomtype.RoomTypeRepository;
import com.Dormitory.service.Services;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

@Service
public class ContractService {
    private ContractRepository contractRepository;
    private StudentRepository studentRepository;
    private SesmesterRepository sesmesterRepository;
    private RoomTypeRepository roomTypeRepository;
    private RoomRepository roomRepository;
    private EmailService emailService;
    @Autowired
    public ContractService(EmailService emailService,ContractRepository contractRepository, StudentRepository studentRepository, SesmesterRepository sesmesterRepository,RoomTypeRepository roomTypeRepository,RoomRepository roomRepository) {
        this.contractRepository = contractRepository;
        this.studentRepository = studentRepository;
        this.sesmesterRepository = sesmesterRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository=roomRepository;
        this.emailService = emailService;
    }
    public Page<ContractResponseDTO> getContractFromFilter(String search, Pageable pageable) {
        Page<Contract> contractPage = contractRepository.searchByStudentNameOrNumber(search, search, pageable);
        List<Contract> contracts = new ArrayList<>(contractPage.getContent());
        List<ContractResponseDTO> contractDTOs = convertToDTOs(contracts);
        return new PageImpl<>(contractDTOs, pageable, contractPage.getTotalElements());
    }
    public Page<ContractResponseDTO> getContractFromFilter(
        Integer sesmester,
        String schoolYear,
        String major,
        String numberStudent,
        Integer gender,
        Pageable pageable
    ) { 
        // Sort sort = Sort.by(Sort.Order.asc("roomType"), Sort.Order.asc("numberRoom"));
        
        Page<Contract> contractsPage = contractRepository.findByFilter(sesmester, schoolYear, major, numberStudent, gender, pageable);
        List<Contract> contracts = new ArrayList<>(contractsPage.getContent());
        List<ContractResponseDTO> contractDTOs = convertToDTOs(contracts);
        
        return new PageImpl<>(contractDTOs, pageable, contractsPage.getTotalElements());
    }

    public List<ContractResponseDTO> convertToDTOs(List<Contract> contracts) {
        List<ContractResponseDTO> contractResponseDTOs = new ArrayList<>();
        for(Contract c : contracts) {

            
            ContractResponseDTO contractResponseDTO = new ContractResponseDTO();
            contractResponseDTO.setStudentId(c.getStudent().getId());
            contractResponseDTO.setId(c.getId());
            contractResponseDTO.setStatus(c.getStatus());
            contractResponseDTO.setTotalPrice(c.getTotalPrice());
            contractResponseDTO.setStudentStatus(c.getStudent().getStatus());
            contractResponseDTO.setNumberStudent(c.getStudent().getNumberStudent());
            contractResponseDTO.setClassroom(c.getStudent().getClassroom());
            contractResponseDTO.setEmail(c.getStudent().getEmail());
            contractResponseDTO.setName(c.getStudent().getName());
            contractResponseDTO.setMajor(c.getStudent().getMajor());
            contractResponseDTO.setPhone(c.getStudent().getPhone());
            contractResponseDTO.setGender(c.getStudent().getGender());
            contractResponseDTO.setRoomType(c.getRoomType());
            contractResponseDTO.setNumberRoom(c.getNumberRoom());
            //Kiểm tra xem  biến c này có nằm trong học kỳ hiện tại hay không
            //B1: LẤY SESMESTER từ biến c
            Sesmester sesmester = c.getSesmester();
            //B2: Lấy startDate và endDate từ biến sesmester kẹp chặt ngay hiện tại
            if(sesmesterRepository.findSesmesterByCurrentDateBetweenStartDateAndEndDate2(LocalDate.now(), sesmester.getStartDate(), sesmester.getEndDate()).isPresent()) {
                contractResponseDTO.setIsCurrentSesmester(true);
            }
            contractResponseDTOs.add(contractResponseDTO);
        }
        return contractResponseDTOs;
    }

    public List<Student> getAllStudentsFromContract(Integer roomTypeId, Integer numberRoom) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
        .orElseThrow(() -> new NotFoundException("Không tìm thấy loại phòng với id: "+roomTypeId));
        Sesmester sesmester = sesmesterRepository.findSesmesterByCurrentDateBetweenStartDateAndEndDate(LocalDate.now())
        .orElseThrow(() -> new NotFoundException("Không tìm thấy học kỳ này"));
        List<Contract> contracts = contractRepository.findByRoomTypeAndNumberRoomAndSesmesterId(roomType.getName(),numberRoom,sesmester.getId());
        
        List<Student> students = new ArrayList<>();
        for(Contract c : contracts) {
            if(c.getStatus() == 1 || c.getStatus() ==0 || c.getStatus() ==3) {
                Student s = studentRepository.findById(c.getStudent().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên với id: "+c.getStudent().getId()));
                students.add(s);
            }
        }
        return students;
    }
    public Contract getContract(Integer studentId, Integer sesmesterId) {
        studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên với id: "+studentId));
        sesmesterRepository.findById(sesmesterId).orElseThrow(() -> new NotFoundException("Không tồn tại học kỳ với id: "+sesmesterId));
        sesmesterRepository.findByIdAndStatus(sesmesterId, true).orElseThrow(() -> new SesmesterDateValidationException("Học kỳ đang đóng"));
        if(contractRepository.findBySesmesterIdAndStudentId(sesmesterId,studentId).isPresent()) {
            return contractRepository.findBySesmesterIdAndStudentId(sesmesterId, studentId).get();
        }
        throw new NotFoundException("Không có hợp đồng với sinh viên này trong học kỳ này");
    }

    public void addContract(Contract contract) {
        //Kiểm tra đầu vào
        Student student = studentRepository.findById(contract.getStudent().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên với id: "+contract.getStudent().getId()));
        sesmesterRepository.findById(contract.getSesmester().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại học kỳ với id: "+contract.getSesmester().getId()));
        Sesmester sesmester = sesmesterRepository.findByIdAndStatus(contract.getSesmester().getId(), true).orElseThrow(() -> new SesmesterDateValidationException("Học kỳ đang đóng"));
        RoomType roomType = roomTypeRepository.findByName(contract.getRoomType()).orElseThrow(() -> new NotFoundException("Không tồn tại tên loại phòng là: "+contract.getRoomType()));
        if(contractRepository.findBySesmesterIdAndStudentId(contract.getSesmester().getId(),contract.getStudent().getId()).isPresent()) {
            throw new AlreadyExistsException("Sinh viên này đã đăng ký học kỳ này rồi");
        }
        
        //Phần xử lý tính tổng giá nguyên học kì 
        LocalDate startDate = sesmester.getStartDate();
        LocalDate endDate = sesmester.getEndDate();
        int holidayWeek = sesmester.getHolidayWeek();
        // Tính số tháng giữa a và b
        Period period = Period.between(startDate, endDate);
        int months = period.getMonths();
        // Tính số tuần giữa startDate và endDate
        int weeks = (int)startDate.until(endDate, java.time.temporal.ChronoUnit.WEEKS);
        Float weeklyPrice = roomType.getPrice()/4;
        contract.setTotalPrice(weeklyPrice*weeks - holidayWeek*weeklyPrice);
        //Tăng số lượng phòng
        // Kiểm tra xem roomType có tồn tại và có còn phòng trống không
        Room room = roomRepository.findByNumberRoomAndRoomType_Id(contract.getNumberRoom(),roomType.getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại phòng "+contract.getNumberRoom()+" thuộc loại phòng"+roomType.getName()));
        if(room.getGender()!=student.getGender()) {
            throw new InvalidValueException("Vui lòng chọn phòng phù hợp với giới tính của bạn");
        }
        if(room.getCurrentQuantity() < roomType.getMaxQuantity()) {
            room.setCurrentQuantity(room.getCurrentQuantity()+1);
        }else {
            throw new InvalidValueException("Phòng này đã đủ số lượng rồi");
        }
        Email email = new Email(student.getEmail(), "THÔNG BÁO ĐẶT PHÒNG THÀNH CÔNG VÀ CÁC QUY ĐỊNH VỀ THỜI GIAN",null);
        emailService.sendEmail(email, student,roomType,room,sesmester,contract);
        // Lưu hợp đồng vào CSDL
        contractRepository.save(contract);
    }
    
    
}
