package com.Dormitory.student;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT u.username FROM Student s JOIN s.user u WHERE s.id = :studentId")
    String findUsernameByStudentId(@Param("studentId") Integer studentId);

    Optional<Student> findByNumberStudent(String numberStudent);
}
