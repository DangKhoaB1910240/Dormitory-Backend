package com.Dormitory.sesmester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesmesterService {
    
    @Autowired
    private SesmesterRepository sesmesterRepository;

    public Sesmester getSesmesterByStatus(Boolean status) {
        return sesmesterRepository.findByStatus(status);
    }
}
