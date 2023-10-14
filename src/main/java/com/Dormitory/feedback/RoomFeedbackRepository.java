package com.Dormitory.feedback;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomFeedbackRepository extends JpaRepository<RoomFeedback, Integer> {
    Optional<List<RoomFeedback>> findByStudentId(Integer studentId);
}
