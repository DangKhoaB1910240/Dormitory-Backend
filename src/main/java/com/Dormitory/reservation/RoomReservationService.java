package com.Dormitory.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.exception.room.RoomNotEnabledException;
import com.Dormitory.exception.roomreservation.NotSuitableForGender;
import com.Dormitory.exception.sesmester.SesmesterDateValidationException;
import com.Dormitory.room.Room;
import com.Dormitory.room.RoomRepository;
import com.Dormitory.roomtype.RoomType;
import com.Dormitory.roomtype.RoomTypeRepository;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class RoomReservationService {
    
    private final RoomReservationRepository roomReservationRepository;
    private final SesmesterRepository sesmesterRepository;
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;
    private final RoomTypeRepository roomTypeRepository;

    @Autowired
    public RoomReservationService(RoomReservationRepository roomReservationRepository,
            SesmesterRepository sesmesterRepository, RoomRepository roomRepository,
            StudentRepository studentRepository,RoomTypeRepository roomTypeRepository) {
        this.roomReservationRepository = roomReservationRepository;
        this.sesmesterRepository = sesmesterRepository;
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    public void deleteById(Integer roomReservationId) {
        roomReservationRepository.deleteById(roomReservationId);
    }
    @Transactional
    public RoomReservation updateRoomReservationStatusAndNote(Integer id, RoomReservation updatedReservation) {
        // Tìm RoomReservation theo ID
        RoomReservation existingReservation = roomReservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy RoomReservation với ID: " + id));

        // Cập nhật trạng thái và ghi chú nếu chúng được cung cấp
        if (updatedReservation.getStatus() != null) {
            existingReservation.setStatus(updatedReservation.getStatus());
        }
        if(updatedReservation.getStatus() == 1) {
            if(existingReservation.getRoom().getCurrentQuantity()>=  existingReservation.getRoom().getRoomType().getMaxQuantity()) {
                throw new InvalidValueException("Số lượng sinh viên trong phòng đã đủ");
            }
            if(existingReservation.getRoom().getEnable() == false) {
                throw new InvalidValueException("Phòng đang sửa chữa");
            }
        }
        
        if (updatedReservation.getNote() != null) {
            existingReservation.setNote(updatedReservation.getNote());
        }

        // Lưu lại RoomReservation đã cập nhật
        return roomReservationRepository.save(existingReservation);
    }
    public List<RoomReservationResponseDTO> getAllRoomReservationBySesmesterIsTrue() {
        List<RoomReservation> roomReservations= roomReservationRepository.findAllBySesmesterStatusOrderByBookingDateTimeAsc(true) ;
        List<RoomReservationResponseDTO> responses = new ArrayList<>();
        for(RoomReservation r : roomReservations) {
            Optional<RoomType> roomType = roomTypeRepository.findByRooms_Id(r.getRoom().getId());
                RoomReservationResponseDTO roomReservationResponseDTO = new RoomReservationResponseDTO();
                roomReservationResponseDTO.setId(r.getId());
                roomReservationResponseDTO.setBookingDateTime(r.getBookingDateTime());
                roomReservationResponseDTO.setNote(r.getNote());
                roomReservationResponseDTO.setStatus(r.getStatus());
                roomReservationResponseDTO.setRoomType(roomType.get());
                roomReservationResponseDTO.setRoom(r.getRoom());
                roomReservationResponseDTO.setSesmester(r.getSesmester());
                roomReservationResponseDTO.setStudent(r.getStudent());
                responses.add(roomReservationResponseDTO);
        }return responses;
        
    }

    public RoomReservationResponseDTO getRoomReservationByNoStudentAndSesmesterIsTrue(String noStudent) {
        Optional<RoomReservation> roomReservation= roomReservationRepository.findRoomReservationsByStudentNumberAndSesmesterStatusIsTrue(noStudent) ;
        if(roomReservation.isPresent()) {
            RoomReservation r = roomReservation.get();
            Optional<RoomType> roomType = roomTypeRepository.findByRooms_Id(r.getRoom().getId());
            if(roomType.isPresent()) {
                RoomReservationResponseDTO roomReservationResponseDTO = new RoomReservationResponseDTO();
                roomReservationResponseDTO.setId(r.getId());
                roomReservationResponseDTO.setBookingDateTime(r.getBookingDateTime());
                roomReservationResponseDTO.setNote(r.getNote());
                roomReservationResponseDTO.setStatus(r.getStatus());
                roomReservationResponseDTO.setRoomType(roomType.get());
                roomReservationResponseDTO.setRoom(r.getRoom());
                roomReservationResponseDTO.setSesmester(r.getSesmester());
                return roomReservationResponseDTO;
            }
            throw new NotFoundException("Không tồn tại loại phòng với phòng có id: "+r.getRoom().getId());
        }
        throw new NotFoundException("Không có thông tin đăng ký phòng với sinh viên có MSSV là: "+noStudent);
    }

    

    @Transactional
    public void addRoomReservation(RoomReservationRequestDTO roomReservationDTO) {
        // Check sesmester is exist
        Sesmester sesmester = sesmesterRepository.findById(roomReservationDTO.getSesmester_id())
        .orElseThrow(()-> new NotFoundException("Học kỳ không tồn tại với id: "+roomReservationDTO.getSesmester_id()));
        // Check room is exist
        Room room = roomRepository.findById(roomReservationDTO.getRoom_id())
        .orElseThrow(()-> new NotFoundException("Phòng không tồn tại với id: "+roomReservationDTO.getRoom_id()));
        // Check student is exist
        Student student = studentRepository.findById(roomReservationDTO.getStudent_id())
        .orElseThrow(()-> new NotFoundException("Không tồn tại sinh viên với id: "+roomReservationDTO.getStudent_id()));
        //Check the room's permission
        if(room.getEnable() == false) {
            throw new RoomNotEnabledException("Phòng hiện không còn hoạt động");
        }
        if(sesmesterRepository.existsByIdAndDateRange(sesmester.getId(), LocalDate.now()) == null){
            throw new SesmesterDateValidationException("Chưa tới thời gian đăng ký phòng ở ký túc xá");
        }
        if(student.getGender() != room.getGender()) {
            throw new NotSuitableForGender("Chọn phòng đúng giới tính của bạn");
        }
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setSesmester(sesmester);
        roomReservation.setRoom(room);
        roomReservation.setStudent(student);

        if(roomReservationRepository.existsByStudentId(roomReservationDTO.getStudent_id())) {
            roomReservationRepository.updateByStudentId(sesmester,room,LocalDateTime.now(),roomReservation.getStudent().getId());
        }else {
            roomReservationRepository.save(roomReservation);
        }
    }
}
