package com.eventsync.controller;

import com.eventsync.dto.CreateEventRequest;
import com.eventsync.dto.EventResponse;
import com.eventsync.dto.EventSentimentSummary;
import com.eventsync.service.EventService;
import com.eventsync.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;
    private final FeedbackService feedbackService;

    @Autowired
    public EventController(EventService eventService, FeedbackService feedbackService) {
        this.eventService = eventService;
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<EventResponse> createEvent(
            @RequestBody CreateEventRequest request) {
        EventResponse response = eventService.createEvent(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEventById(
            @PathVariable Long eventId) {
        EventResponse event = eventService.getEventById(eventId);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/{eventId}/summary")
    public ResponseEntity<EventSentimentSummary> getEventSummary(
            @PathVariable Long eventId) {
        eventService.getEventById(eventId);

        EventSentimentSummary summary = feedbackService.getEventSentimentSummary(eventId);
        return ResponseEntity.ok(summary);
    }
}
