package com.Dormitory.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.exception.room.RoomNotEnabledException;
import com.Dormitory.exception.roomreservation.NotSuitableForGender;
import com.Dormitory.exception.sesmester.SesmesterDateValidationException;
import com.Dormitory.room.Room;
import com.Dormitory.room.RoomRepository;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class RoomReservationService {
    
    private final RoomReservationRepository roomReservationRepository;
    private final SesmesterRepository sesmesterRepository;
    private final RoomRepository roomRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public RoomReservationService(RoomReservationRepository roomReservationRepository,
            SesmesterRepository sesmesterRepository, RoomRepository roomRepository,
            StudentRepository studentRepository) {
        this.roomReservationRepository = roomReservationRepository;
        this.sesmesterRepository = sesmesterRepository;
        this.roomRepository = roomRepository;
        this.studentRepository = studentRepository;
    }

    public List<RoomReservationResponseDTO> getAllRoomReservation() {
        List<RoomReservation> reservations = roomReservationRepository.findAllByOrderByBookingDateTimeAsc();
        List<RoomReservationResponseDTO> responses = new ArrayList<>();
        for(RoomReservation r: reservations) {

            RoomReservationResponseDTO response = new RoomReservationResponseDTO();
            response.setId(r.getId());
            response.setNumberStudent(r.getStudent().getNumberStudent());
            response.setName(r.getStudent().getName());
            response.setEmail(r.getStudent().getEmail());
            response.setPhone(r.getStudent().getPhone());
            response.setRoomTypeName(roomRepository.findRoomTypeNameById(r.getRoom().getId()));
            response.setNumberRoom(r.getRoom().getNumberRoom());
            //Format LocalDateTime 
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            String formattedDateTime = r.getBookingDateTime().format(formatter);
            response.setBookingDateTime(formattedDateTime);
            response.setStatus(r.getStatus());
            response.setNote(r.getNote());
            responses.add(response);
        }
        return responses;
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
        if(sesmesterRepository.existsByIdAndDateRange(sesmester.getId(), LocalDate.now()) == false){
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
            roomReservationRepository.updateByStudentId(sesmester,room,roomReservation.getStudent().getId());
        }else {
            roomReservationRepository.save(roomReservation);
        }
    }
}
