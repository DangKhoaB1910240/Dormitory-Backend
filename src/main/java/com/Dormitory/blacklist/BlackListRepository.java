package com.Dormitory.blacklist;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList,Integer> {
     Page<BlackList> findAll(Pageable pageable);
     Optional<BlackList> findByStudentId(Integer studentId);
}
