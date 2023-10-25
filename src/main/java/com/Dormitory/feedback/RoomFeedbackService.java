package com.Dormitory.feedback;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.admin.Admin;
import com.Dormitory.admin.AdminRepository;
import com.Dormitory.contract.Contract;
import com.Dormitory.contract.ContractRepository;
import com.Dormitory.email.Email;
import com.Dormitory.email.EmailService;
import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.material.Material;
import com.Dormitory.material.MaterialRepository;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.sesmester.SesmesterRepository;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

@Service
public class RoomFeedbackService {
    
    private RoomFeedbackRepository roomFeedbackRepository;
    private StudentRepository studentRepository;
    private MaterialRepository materialRepository;
    private ContractRepository contractRepository;
    private SesmesterRepository sesmesterRepository;
    private EmailService emailService;
    private AdminRepository adminRepository;
    @Autowired
    public RoomFeedbackService(EmailService emailService,RoomFeedbackRepository roomFeedbackRepository, StudentRepository studentRepository,
            MaterialRepository materialRepository, ContractRepository contractRepository,SesmesterRepository sesmesterRepository,AdminRepository adminRepository) {
        this.roomFeedbackRepository = roomFeedbackRepository;
        this.studentRepository = studentRepository;
        this.materialRepository = materialRepository;
        this.contractRepository = contractRepository;
        this.sesmesterRepository = sesmesterRepository;
        this.emailService = emailService;
        this.adminRepository = adminRepository;
    }
    public List<RoomFeedback> getAllFeedbacks() {
        return roomFeedbackRepository.findAll();
    }
    public void updateStatus(Integer id, FeedbackRequestDTO requestDTO) {
        RoomFeedback r = roomFeedbackRepository.findById(id)
        .orElseThrow(() -> new NotFoundException("Không tồn tại phản hồi này"));
        Admin admin = adminRepository.findById(requestDTO.getAdminId()).orElseThrow(() -> new NotFoundException("Không tồn tại admin này"));

        if ( requestDTO.getEditDate().compareTo(LocalDate.now()) <= 0) {
            throw new InvalidValueException("Ngày sửa chữa phải lớn hơn ngày hiện tại");
        }
        r.setAdmin(admin);
        r.setStatus(requestDTO.getStatus());

        Email email = new Email(requestDTO.getStudent().getEmail(),"THÔNG BÁO NGÀY XUỐNG SỬA CHỮA VẬT CHẤT TRONG PHÒNG",null);
        emailService.sendEmailForFeedback(email, requestDTO.getStudent(), requestDTO.getRoomType(), requestDTO.getNumberRoom(), requestDTO.getEditDate());
        
        roomFeedbackRepository.save(r);
    }
    public List<FeedbackResponseDTO> getFeedbackByStudent(Integer id) {
        List<FeedbackResponseDTO> responseDTOs = new ArrayList<>();
        if(roomFeedbackRepository.findByStudentId(id).isPresent()) {
            List<RoomFeedback> roomFeedbacks = roomFeedbackRepository.findByStudentId(id).get();
            for(RoomFeedback r: roomFeedbacks) {
                FeedbackResponseDTO feedbackResponseDTO = new FeedbackResponseDTO();
                feedbackResponseDTO.setId(r.getId());
                feedbackResponseDTO.setQuantity(r.getQuantity());
                feedbackResponseDTO.setStatus(r.getStatus());
                feedbackResponseDTO.setSendDate(r.getSendDate());
                Material material = materialRepository.findById(r.getMaterial().getId())
                .orElseThrow(() -> new NotFoundException("Không tồn tại cơ sở vật chất này"));
                feedbackResponseDTO.setContent(material.getName());
                responseDTOs.add(feedbackResponseDTO);
            }
        }
        return responseDTOs;
    }
    public void addFeedBackFromStudent(RoomFeedback roomFeedback) {
        Student student = studentRepository.findById(roomFeedback.getStudent().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại sinh viên với id: "+roomFeedback.getStudent().getId()));
        Sesmester sesmester = sesmesterRepository.findSesmesterByCurrentDateBetweenStartDateAndEndDate(roomFeedback.getSendDate())
        .orElseThrow(() -> new NotFoundException("Học kỳ này chưa hoạt động"));
        Contract contract = contractRepository.findBySesmesterIdAndStudentId(sesmester.getId(), student.getId())
        .orElseThrow(() -> new NotFoundException("Sinh viên chưa đăng ký phòng ở học kỳ này"));
        materialRepository.findById(roomFeedback.getMaterial().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại cơ sở vật chất này"));
        if(roomFeedback.getQuantity()<=0) {
            throw new InvalidValueException("Số lượng phải lớn hơn 0");
        }
        roomFeedback.setNumberRoom(contract.getNumberRoom());
        roomFeedback.setRoomType(contract.getRoomType());
        roomFeedbackRepository.save(roomFeedback);
    }
    
}
