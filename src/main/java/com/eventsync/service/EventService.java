package com.eventsync.service;

import com.eventsync.dto.CreateEventRequest;
import com.eventsync.dto.EventResponse;
import com.eventsync.model.Event;
import com.eventsync.exception.EventNotFoundException;
import com.eventsync.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventResponse createEvent(CreateEventRequest request) {
        Event event = new Event(request.getTitle(), request.getDescription());
        Event savedEvent = eventRepository.save(event);
        return convertToResponse(savedEvent);
    }

    public List<EventResponse> getAllEvents() {
        List<Event> events = eventRepository.findAllByOrderByCreatedAtDesc();
        return events.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public EventResponse getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));
        return convertToResponse(event);
    }

    public Event getEventEntityById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + eventId));
    }

    public List<EventResponse> searchEventsByTitle(String title) {
        List<Event> events = eventRepository.findByTitleContainingIgnoreCase(title);
        return events.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private EventResponse convertToResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getCreatedAt()
        );
    }
}
