package com.Dormitory.sesmester;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SesmesterService {
    
    @Autowired
    private SesmesterRepository sesmesterRepository;

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
}
