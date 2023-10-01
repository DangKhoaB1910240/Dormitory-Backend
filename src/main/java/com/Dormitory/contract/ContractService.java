package com.Dormitory.contract;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.admin.Admin;
import com.Dormitory.admin.AdminRepository;
import com.Dormitory.exception.AlreadyExistsException;
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
    private AdminRepository adminRepository;
    private SesmesterRepository sesmesterRepository;
    private RoomTypeRepository roomTypeRepository;
    private RoomRepository roomRepository;
    @Autowired
    public ContractService(ContractRepository contractRepository, StudentRepository studentRepository,
            AdminRepository adminRepository, SesmesterRepository sesmesterRepository,RoomTypeRepository roomTypeRepository,RoomRepository roomRepository) {
        this.contractRepository = contractRepository;
        this.studentRepository = studentRepository;
        this.adminRepository = adminRepository;
        this.sesmesterRepository = sesmesterRepository;
        this.roomTypeRepository = roomTypeRepository;
        this.roomRepository=roomRepository;
    }

    public void addContract(Contract contract) {
        //Kiểm tra đầu vào
        studentRepository.findById(contract.getStudent().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên với id: "+contract.getStudent().getId()));
        adminRepository.findById(contract.getAdmin().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại admin với id: "+contract.getAdmin().getId()));
        sesmesterRepository.findById(contract.getSesmester().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại học kỳ với id: "+contract.getSesmester().getId()));
        Sesmester sesmester = sesmesterRepository.findByIdAndStatus(contract.getSesmester().getId(), true).orElseThrow(() -> new SesmesterDateValidationException("Học kỳ đang đóng"));
        
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
        RoomType roomType = roomTypeRepository.findByName(contract.getRoomType()).orElseThrow(() -> new NotFoundException("Không tồn tại tên loại phòng là: "+contract.getRoomType()));
        Float weeklyPrice = roomType.getPrice()/4;
        contract.setTotalPrice(weeklyPrice*weeks);
    //Tăng số lượng phòng
        // Kiểm tra xem roomType có tồn tại và có còn phòng trống không
    if (roomType != null && roomType.getEnable()) {
        // Lấy ra danh sách phòng thuộc loại roomType
        List<Room> rooms = roomType.getRooms();

        // Tìm phòng có số lượng còn trống và số phòng trùng với numberRoom từ contract
        Optional<Room> selectedRoom = rooms.stream()
            .filter(room -> room.getCurrentQuantity() < roomType.getMaxQuantity() && room.getNumberRoom().equals(contract.getNumberRoom()))
            .findFirst();

        // Kiểm tra xem có phòng phù hợp không
        if (selectedRoom.isPresent()) {
            Room roomToUpdate = selectedRoom.get();

            // Tăng số lượng phòng đã đặt
            roomToUpdate.setCurrentQuantity(roomToUpdate.getCurrentQuantity() + 1);

            // Lưu lại thông tin phòng
            roomRepository.save(roomToUpdate);
        } else {
            throw new NotFoundException("Phòng này đã đủ số lượng rồi");
        }
    }
        // Lưu hợp đồng vào CSDL
        contractRepository.save(contract);
    }

    public void registerServices(Integer contractId, List<Services> services) {
        // Tìm hợp đồng theo ID
        Contract contract = contractRepository.findById(contractId)
            .orElseThrow(() -> new NotFoundException("Không tồn tại hợp đồng với ID: " + contractId));
        // Kiểm tra bên trong services
        for(Services s: services) {
            if (!s.getEnable()) {
                throw new NotFoundException("Dịch vụ " + s.getName() + " không được kích hoạt.");
            }
            
            // Kiểm tra xem bảng liên kết đã có cặp id contract và id service hay chưa
            if (contract.getServices().stream().anyMatch(service -> service.getId().equals(s.getId()))) {
                throw new AlreadyExistsException("Dịch vụ " + s.getName() + " đã được đăng ký trong hợp đồng này.");
            }

        }
        // Thêm dịch vụ vào danh sách services của hợp đồng
        contract.getServices().addAll(services);

        // Lưu hợp đồng đã cập nhật vào CSDL
        contractRepository.save(contract);
    }
    
}
