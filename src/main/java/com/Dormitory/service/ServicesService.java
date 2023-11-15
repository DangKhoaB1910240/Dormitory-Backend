package com.Dormitory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;

@Service
public class ServicesService {
    
    @Autowired
    private ServicesRepository serviceRepository;

    public List<Services> getAllService() {
        return serviceRepository.findAll();
    }
    
    public void updateService(Services services) {
        Services service = serviceRepository.findById(services.getId()).orElseThrow(() -> new NotFoundException("Không tồn tại dịch vụ với id: "+services.getId()));
        service.setDescription(services.getDescription());
        service.setEnable(services.getEnable());
        service.setId(services.getId());
        service.setName(services.getName());
        service.setPrice(services.getPrice());
        serviceRepository.save(service);
    }
    public Services getServiceById(Integer id) {
        return serviceRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại dịch vụ này"));
    }
    public void createService(Services service) {
        if(service.getPrice() <0 && service.getPrice()%1000!=0) {
            throw new InvalidValueException("Nhâp giá lớn hơn 0 và chia hết cho 1000");
        }
        serviceRepository.save(service);
    }
}
