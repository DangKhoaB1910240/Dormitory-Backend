package com.Dormitory.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dormitory.role.Role;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    boolean existsByUsername(String username);
    
    Optional<User> findByUsername(String username); // Giai quyết vấn đề bị null

}
