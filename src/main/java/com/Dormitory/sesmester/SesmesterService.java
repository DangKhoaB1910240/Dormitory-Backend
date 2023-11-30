package com.Dormitory.sesmester;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.room.Room;
import com.Dormitory.room.RoomRepository;

import jakarta.transaction.Transactional;

@Service
public class SesmesterService {
    
    @Autowired
    private SesmesterRepository sesmesterRepository;
    @Autowired
    private RoomRepository roomRepository;
    public Sesmester getSesmesterByStatus(Boolean status) {
        return sesmesterRepository.findByStatus(status);
    }
    public List<Sesmester> getAllSesmester() {
        Sort sort = Sort.by(Sort.Order.asc("schoolYear"), Sort.Order.asc("sesmester"));
        return sesmesterRepository.findAll(sort);
        
    }
    // @Scheduled(cron = "0 21 7 * * ?") // Chạy mỗi ngày lúc 7:21 sáng
    // public void heelo() {
    //     System.out.println("aaa");
    // }
    @Transactional
    public void createSesmester(Sesmester sesmester) {
        if(sesmester.getEndDate().isBefore(sesmester.getStartDate()) || sesmester.getEndDate().isEqual(sesmester.getStartDate())) {
            throw new InvalidValueException("Ngày kết thúc phải lớn hơn ngày bắt đầu học kỳ");
        }
        if(sesmester.getRegistrationEndDate().isBefore(sesmester.getRegistrationStartDate()) || sesmester.getRegistrationEndDate().isEqual(sesmester.getRegistrationStartDate())) {
            throw new InvalidValueException("Ngày kết thúc đăng ký phải lớn hơn ngày bắt đầu đăng ký");
        }
        if(sesmester.getRegistrationEndDate().isAfter(sesmester.getStartDate()) || sesmester.getRegistrationEndDate().isEqual(sesmester.getStartDate())) {
            throw new InvalidValueException("Ngày kết thúc đăng ký phải nhỏ hơn ngày bắt đầu học kỳ");
        }
        if(sesmester.getHolidayWeek() <0 || sesmester.getHolidayWeek() >4) {
            throw new InvalidValueException("Số tuần nghĩ từ 0 đến 4");
        }
        for(Sesmester s: sesmesterRepository.findAll()) {
            if(sesmester.getStartDate().isBefore(s.getEndDate()) || sesmester.getStartDate().isEqual(s.getEndDate())) {
                throw new InvalidValueException("Kiểm tra kỹ có sự trùng lặp về ngày");
            }
        }
        sesmesterRepository.save(sesmester);
        for(Room r : roomRepository.findAll()) {
            r.setCurrentQuantity(0);
            roomRepository.save(r);
        }
        
    }
}
