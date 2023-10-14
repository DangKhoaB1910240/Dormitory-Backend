package com.Dormitory.material;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialService {
    @Autowired
    private MaterialRepository materialRepository;
    public List<Material> getAllMaterial() {
        return materialRepository.findAll();
    }
}
