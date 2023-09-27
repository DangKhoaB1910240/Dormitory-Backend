package com.Dormitory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicesService {
    
    @Autowired
    private ServicesRepository serviceRepository;

    public List<Services> getAllService() {
        return serviceRepository.findAll();
    }
}
