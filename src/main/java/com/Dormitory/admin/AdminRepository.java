package com.Dormitory.admin;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{
    Optional<Admin> findByNumberAdmin(String numberAdmin);
}
