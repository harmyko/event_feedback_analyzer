package com.eventsync.controller;

import com.eventsync.dto.CreateFeedbackRequest;
import com.eventsync.dto.FeedbackResponse;
import com.eventsync.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<FeedbackResponse> addFeedback(
            @PathVariable Long eventId, @RequestBody CreateFeedbackRequest request) {

        FeedbackResponse response = feedbackService.createFeedback(eventId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> getEventFeedback(@PathVariable Long eventId) {
        List<FeedbackResponse> response = feedbackService.getEventFeedback(eventId);
        return ResponseEntity.ok(response);
    }
}
