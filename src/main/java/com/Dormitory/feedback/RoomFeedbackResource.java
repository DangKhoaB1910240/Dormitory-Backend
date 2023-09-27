package com.Dormitory.feedback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/feedback")
public class RoomFeedbackResource {
    @Autowired
    private RoomFeedbackService roomFeedbackService;

    @PostMapping()
    public ResponseEntity<?> addFeedBackFromStudent(@Valid @RequestBody RoomFeedback roomFeedback) {
        roomFeedbackService.addFeedBackFromStudent(roomFeedback);
        return ResponseEntity.ok().build();
    }
}
