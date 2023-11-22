package com.Dormitory.blacklist;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Dormitory.contract.Contract;
import com.Dormitory.contract.ContractRepository;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.room.Room;
import com.Dormitory.room.RoomRepository;
import com.Dormitory.roomtype.RoomType;
import com.Dormitory.roomtype.RoomTypeRepository;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.sesmester.SesmesterService;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

@Service
public class BlackListService {
    @Autowired
    private BlackListRepository blackListRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private SesmesterRepository sesmesterRepository;
    @Scheduled(cron = "0 0 0 * * *") 
    public void addBlackList() {
        //Kiểm tra 0 giờ mỗi ngày nếu không có thì them vào danh sách đen
        List<Contract> contracts = contractRepository.findAll();
        for(Contract c: contracts) {
            Sesmester sesmester = c.getSesmester();
            Student student = c.getStudent();
            BlackList blackList = new BlackList();
            if((LocalDate.now().isEqual(sesmester.getStartDate()) || LocalDate.now().isAfter(sesmester.getStartDate())) && c.getStatus() == 0) {
                c.setStatus(2);
                contractRepository.save(c);
                student.setStatus(2);
                studentRepository.save(student);
                blackList.setReason("Không đóng tiền phòng đúng hẹn");
                blackList.setStudent(student);
                blackList.setAdmin(null);
                blackListRepository.save(blackList);
                RoomType roomType = roomTypeRepository.findByName(c.getRoomType())
                .orElseThrow(() -> new NotFoundException("Không tìm thấy loại phòng này"));
                Room room = roomRepository.findByNumberRoomAndRoomType_Id(c.getNumberRoom(), roomType.getId())
                .orElseThrow(() -> new NotFoundException("Không tồn tại loại phòng này"));
                room.setCurrentQuantity(room.getCurrentQuantity()-1);
                roomRepository.save(room);
            }
        }
    }
    public void createBlackList(BlackList blackList) {
        Student student = studentRepository.findById(blackList.getStudent().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên này"));
        student.setStatus(2);
        studentRepository.save(student);
        Sesmester sesmester = sesmesterRepository.findSesmesterByCurrentDateBetweenStartDateAndEndDate(LocalDate.now())
        .orElseThrow(() -> new NotFoundException("Không tồn tại học kỳ này"));
        Contract contract = contractRepository.findBySesmesterIdAndStudentId(sesmester.getId(),student.getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại hợp đồng"));
        RoomType roomType = roomTypeRepository.findByName(contract.getRoomType())
        .orElseThrow(() -> new NotFoundException("Không tìm thấy loại phòng này"));
        Room room = roomRepository.findByNumberRoomAndRoomType_Id(contract.getNumberRoom(), roomType.getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại loại phòng này"));
        room.setCurrentQuantity(room.getCurrentQuantity()-1);
        contract.setStatus(2);
        contractRepository.save(contract);
        blackListRepository.save(blackList);
    }
    public Page<BlackList> getAllBlackLists(Pageable pageable) {
        return blackListRepository.findAll(pageable);
    }
}
