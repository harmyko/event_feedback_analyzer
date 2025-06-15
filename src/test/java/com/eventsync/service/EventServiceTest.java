package com.eventsync.service;

import com.eventsync.dto.CreateEventRequest;
import com.eventsync.dto.EventResponse;
import com.eventsync.exception.EventNotFoundException;
import com.eventsync.model.Event;
import com.eventsync.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_ShouldReturnEventResponse() {
        CreateEventRequest request = new CreateEventRequest();
        request.setTitle("Test Event");
        request.setDescription("Test Description");

        Event savedEvent = new Event("Test Event", "Test Description");
        savedEvent.setId(1L);
        savedEvent.setCreatedAt(LocalDateTime.now());

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        EventResponse response = eventService.createEvent(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Event", response.getTitle());
    }

    @Test
    void getEventById_WhenEventExists_ShouldReturnEvent() {
        Long eventId = 1L;
        Event event = new Event("Test Event", "Description");
        event.setId(eventId);
        event.setCreatedAt(LocalDateTime.now());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        EventResponse response = eventService.getEventById(eventId);

        assertNotNull(response);
        assertEquals(eventId, response.getId());
    }

    @Test
    void getEventById_WhenEventNotExists_ShouldThrowException() {
        Long eventId = 999L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(eventId));
    }
}