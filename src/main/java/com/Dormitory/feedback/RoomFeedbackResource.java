package com.Dormitory.feedback;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/feedback")
public class RoomFeedbackResource {
    @Autowired
    private RoomFeedbackService roomFeedbackService;

    @PostMapping()
    public ResponseEntity<?> addFeedBackFromStudent(@Valid @RequestBody RoomFeedback roomFeedback) {
        roomFeedbackService.addFeedBackFromStudent(roomFeedback);
        return ResponseEntity.ok().build();
    }
    @GetMapping("student/{id}")
    public ResponseEntity<List<FeedbackResponseDTO>> getFeedbackByStudent(@PathVariable("id") Integer studentId) {
        return ResponseEntity.status(HttpStatus.OK).body(roomFeedbackService.getFeedbackByStudent(studentId));    }
    @GetMapping()
    public ResponseEntity<List<RoomFeedback>> getAllFeedbacks() {
        return ResponseEntity.status(HttpStatus.OK).body(roomFeedbackService.getAllFeedbacks());
    }
    @PatchMapping("{id}")
    public ResponseEntity<Void> updateStatus(@PathVariable("id") Integer id,@RequestBody FeedbackRequestDTO requestDTO) {
        roomFeedbackService.updateStatus(id,requestDTO);
        return ResponseEntity.noContent().build();
    }
}
