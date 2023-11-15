package com.Dormitory.bill;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.Dormitory.admin.Admin;
import com.Dormitory.admin.AdminRepository;
import com.Dormitory.contract.Contract;
import com.Dormitory.contract.ContractRepository;
import com.Dormitory.email.Email;
import com.Dormitory.email.EmailService;
import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.room.Room;
import com.Dormitory.room.RoomRepository;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class BillService {
    private BillRepository billRepository;
    private RoomRepository roomRepository;
    private AdminRepository adminRepository;
    private SesmesterRepository sesmesterRepository;
    private ContractRepository contractRepository;
    private EmailService emailService;
    private StudentRepository studentRepository;
    @Autowired
    public BillService(StudentRepository studentRepository,ContractRepository contractRepository,EmailService emailService,BillRepository billRepository, RoomRepository roomRepository, AdminRepository adminRepository, SesmesterRepository sesmesterRepository) {
        this.billRepository = billRepository;
        this.roomRepository = roomRepository;
        this.adminRepository = adminRepository;
        this.sesmesterRepository = sesmesterRepository;
        this.emailService = emailService;
        this.contractRepository = contractRepository;
        this.studentRepository = studentRepository;
    }

    public Bill getBillById(Integer id) {
        return billRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại hóa đơn với id: "+id));
    }
    
    public List<Bill> getByRoomTypeAndRoom(String roomType,Integer numberRoom) {
        return billRepository.findByRoomTypeAndNumberRoomOrderByCreatedDateDesc(roomType,numberRoom);
    }
    public void updateBill(Integer id,Bill bill) {
        Bill b = billRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Không tồn tại hóa đơn với id: "+id));
        b.setAdmin2(bill.getAdmin2());
        b.setFinalElectricity(bill.getFinalElectricity());
        b.setFinalWater(bill.getFinalWater());
        Float electricityRate = 1728f; // Giá điện theo kw
        Float waterRate = 5973f; // Giá nước theo khối nước
        Integer electricityConsumed = bill.getFinalElectricity() - bill.getInitialElectricity();
        Integer waterConsumed = bill.getFinalWater() - bill.getInitialWater();
        Float electricityCost = electricityConsumed * electricityRate;
        Float waterCost = waterConsumed * waterRate;
        Float totalCost = electricityCost + waterCost;
        bill.setPrice(totalCost);
        billRepository.save(b);
    }
    public void addBill(Integer adminId,BillRequestDTO requestDTO) {
        Admin admin = adminRepository.findById(adminId)
        .orElseThrow(() -> new NotFoundException("Không tồn tại admin này"));
        
        // Kiểm tra chỉ số chèn vào có lớn hơn 0
        if (requestDTO.getFinalElectricity() <= 0 || requestDTO.getFinalWater() <= 0) {
            throw new InvalidValueException("Chỉ số điện và nước phải lớn hơn 0");
        }
        // Tìm số phòng và loại phòng từ roomId
        Room room = roomRepository.findById(requestDTO.getRoomId())
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy phòng với ID: " + requestDTO.getRoomId()));
        Bill bill = new Bill();
        Integer numberRoom = room.getNumberRoom();
        String roomType = room.getRoomType().getName();
        // Tìm chỉ số điện nước cuối cùng
        Optional<Bill> lastBill = billRepository.findFirstByRoomTypeAndNumberRoomOrderByCreatedDateDesc(roomType,room.getNumberRoom());
        if (lastBill.isPresent()) {
            bill.setInitialElectricity(lastBill.get().getFinalElectricity());
            bill.setInitialWater(lastBill.get().getFinalWater());
        }else {
             bill.setInitialElectricity(0);
            bill.setInitialWater(0);
        }
        bill.setAdmin(admin);
        bill.setFinalElectricity(requestDTO.getFinalElectricity());
        bill.setFinalWater(requestDTO.getFinalWater());
        
        // Kiểm tra xem đã có hóa đơn cho phòng đó trong tháng hiện tại hay chưa
        LocalDate currentDate = LocalDate.now();
        Optional<Bill> latestBills = billRepository.findFirstByRoomTypeAndNumberRoomOrderByCreatedDateDesc(roomType, numberRoom);
        if(latestBills.isPresent()) {
            if (latestBills.get().getCreatedDate().getMonth() == currentDate.getMonth() &&
                latestBills.get().getCreatedDate().getYear() == currentDate.getYear()) {
                throw new AlreadyExistsException("Chỉ được nhập duy nhất một hóa đơn cho mỗi phòng mỗi tháng.");
            }
        }
        if(bill.getFinalElectricity() <= bill.getInitialElectricity()) {
            throw new InvalidValueException("Chỉ số điện cuối phải lớn hơn chỉ số điện đầu");
        }
        if(bill.getFinalWater() <= bill.getInitialWater()) {
            throw new InvalidValueException("Chỉ số điện cuối phải lớn hơn chỉ số điện đầu");
        }
        bill.setRoomType(roomType);
        bill.setNumberRoom(numberRoom);
        bill.setStatus(false);

        // Tính toán tổng tiền điện nước dựa trên kw và khối nước
        // Giả sử có các biến constant cho giá điện và giá nước
        Float electricityRate = 1728f; // Giá điện theo kw
        Float waterRate = 5973f; // Giá nước theo khối nước
        Integer electricityConsumed = bill.getFinalElectricity() - bill.getInitialElectricity();
        Integer waterConsumed = bill.getFinalWater() - bill.getInitialWater();
        Float electricityCost = electricityConsumed * electricityRate;
        Float waterCost = waterConsumed * waterRate;
        Float totalCost = electricityCost + waterCost;
        bill.setPrice(totalCost);
        
        Sesmester sesmester = sesmesterRepository.findSesmesterByCurrentDateBetweenStartDateAndEndDate(currentDate)
        .orElseThrow(() -> new NotFoundException("Không có học kỳ phù hợp với ngày hiện tại"));
        List<Contract> contracts = contractRepository.findByRoomTypeAndNumberRoomAndSesmesterId(roomType, numberRoom, sesmester.getId());
        if(!contracts.isEmpty()) {
            for(Contract c: contracts) {
                Student student = studentRepository.findById(c.getStudent().getId()).orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên với id: "+c.getStudent().getId()));
                Email email = new Email(student.getEmail(), "THÔNG BÁO TIỀN ĐIỆN NƯỚC", null);
                emailService.notificationBills(email, student, roomType, numberRoom, electricityConsumed, waterConsumed, bill);
            }
        }
        billRepository.save(bill);
    }
    public Page<Bill> getAllBills(Pageable pageable) {
        return billRepository.findAllBillsSorted(pageable);
    }
    public List<BillResponseDTO> getStatisticalByDate(LocalDate date) {
        // Lấy danh sách hóa đơn theo ngày và roomType
        List<Bill> bills = billRepository.findByCreatedDateMonthAndCreatedDateYear(date.getMonthValue(),date.getYear());
        // Thực hiện thống kê
        return calculateStatistics(bills);
    }
    private List<BillResponseDTO> calculateStatistics(List<Bill> bills) {
        
        List<BillResponseDTO> billResponseDTOs = new ArrayList<>();

    // Sử dụng Map để theo dõi thông tin theo roomType
    Map<String, BillResponseDTO> roomTypeMap = new HashMap<>();

    for (Bill bill : bills) {
        String roomType = bill.getRoomType();
        BillResponseDTO billResponseDTO = roomTypeMap.getOrDefault(roomType, new BillResponseDTO(roomType));

        billResponseDTO.setTotalPrice(billResponseDTO.getTotalPrice() + bill.getPrice());

        if (bill.getStatus()) {
            billResponseDTO.setCurrentPrice(billResponseDTO.getCurrentPrice() + bill.getPrice());
            billResponseDTO.setNumberOfRoomsPaid(billResponseDTO.getNumberOfRoomsPaid() + 1);
            billResponseDTO.addListRoomsPaids(bill.getNumberRoom());
        } else {
            billResponseDTO.setNumberOfRoomsUnpaid(billResponseDTO.getNumberOfRoomsUnpaid() + 1);
            billResponseDTO.addListRoomsUnpaids(bill.getNumberRoom());
        }

        roomTypeMap.put(roomType, billResponseDTO);
    }

    // Chuyển Map thành danh sách
    billResponseDTOs.addAll(roomTypeMap.values());

    return billResponseDTOs;
    }
}
