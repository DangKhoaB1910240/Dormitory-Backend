package com.Dormitory.contract;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Student> getAllStudentsFromContract(Integer roomTypeId, Integer numberRoom) {
        RoomType roomType = roomTypeRepository.findById(roomTypeId)
        .orElseThrow(() -> new NotFoundException("Không tìm thấy loại phòng với id: "+roomTypeId));
        Sesmester sesmester = sesmesterRepository.findSesmesterByCurrentDateBetweenStartDateAndEndDate(LocalDate.now())
        .orElseThrow(() -> new NotFoundException("Không tìm thấy học kỳ này"));
        List<Contract> contracts = contractRepository.findByRoomTypeAndNumberRoomAndSesmesterId(roomType.getName(),numberRoom,sesmester.getId());
        List<Student> students = new ArrayList<>();
        for(Contract c : contracts) {
            if(c.getStatus() == 1) {
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

    // public void registerServices(Integer contractId, List<Services> services) {
    //     // Tìm hợp đồng theo ID
    //     Contract contract = contractRepository.findById(contractId)
    //         .orElseThrow(() -> new NotFoundException("Không tồn tại hợp đồng với ID: " + contractId));
    //     //Tìm học kỳ    
    //     Sesmester sesmester = sesmesterRepository.findByIdAndStatus(contract.getSesmester().getId(), true).orElseThrow(() -> new SesmesterDateValidationException("Học kỳ đang đóng"));
    //     // Kiểm tra bên trong services
    //     for(Services s: services) {
    //         if (!s.getEnable()) {
    //             throw new NotFoundException("Dịch vụ " + s.getName() + " không được kích hoạt.");
    //         }
            
    //         // Kiểm tra xem bảng liên kết đã có cặp id contract và id service hay chưa
    //         if (contract.getServices().stream().anyMatch(service -> service.getId().equals(s.getId()))) {
    //             throw new AlreadyExistsException("Dịch vụ " + s.getName() + " đã được đăng ký trong hợp đồng này.");
    //         }

    //     }
    //     // Thêm dịch vụ vào danh sách services của hợp đồng
    //     contract.getServices().addAll(services);
    //     // 
    //     //Phần xử lý tính tổng giá nguyên học kì 
    //     LocalDate startDate = sesmester.getStartDate();
    //     LocalDate endDate = sesmester.getEndDate();
    //     int holidayWeek = sesmester.getHolidayWeek();
    //     // Tính số tháng giữa a và b
    //     Period period = Period.between(startDate, endDate);
    //     int months = period.getMonths();
    //     // Tính số tuần giữa startDate và endDate
    //     int weeks = (int)startDate.until(endDate, java.time.temporal.ChronoUnit.WEEKS);
    //     RoomType roomType = roomTypeRepository.findByName(contract.getRoomType()).orElseThrow(() -> new NotFoundException("Không tồn tại tên loại phòng là: "+contract.getRoomType()));
    //     Float weeklyPrice = roomType.getPrice()/4;
    //     //Lấy tổng dịch vụ
    //     Float totalPriceOfService = 0F;
    //     for(Services s : contract.getServices()) {
    //         totalPriceOfService += s.getPrice()/4;
    //     }
    //     contract.setTotalPrice(contract.getTotalPrice()+ totalPriceOfService*weeks - totalPriceOfService*holidayWeek);
    //     // Lưu hợp đồng đã cập nhật vào CSDL
    //     contractRepository.save(contract);
    // }
    
}
