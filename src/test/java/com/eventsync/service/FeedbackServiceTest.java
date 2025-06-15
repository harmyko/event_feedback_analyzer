package com.eventsync.service;

import com.eventsync.dto.CreateFeedbackRequest;
import com.eventsync.dto.EventSentimentSummary;
import com.eventsync.dto.FeedbackResponse;
import com.eventsync.exception.EventNotFoundException;
import com.eventsync.model.Feedback;
import com.eventsync.repository.EventRepository;
import com.eventsync.repository.FeedbackRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private FeedbackRepository feedbackRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SentimentService sentimentService;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    void createFeedback_WhenEventExists_ShouldReturnFeedbackResponse() {
        Long eventId = 1L;
        CreateFeedbackRequest request = new CreateFeedbackRequest();
        request.setUsername("testuser");
        request.setContent("Great event!");

        when(eventRepository.existsById(eventId)).thenReturn(true);
        when(sentimentService.analyzeSentiment("Great event!")).thenReturn("POSITIVE");

        Feedback savedFeedback = new Feedback(eventId, "testuser", "Great event!");
        savedFeedback.setId(1L);
        savedFeedback.setSentiment("POSITIVE");
        savedFeedback.setCreatedAt(LocalDateTime.now());

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(savedFeedback);

        FeedbackResponse response = feedbackService.createFeedback(eventId, request);

        assertNotNull(response);
        assertEquals(eventId, response.getEventId());
        assertEquals("testuser", response.getUsername());
        assertEquals("POSITIVE", response.getSentiment());
    }

    @Test
    void createFeedback_WhenEventNotExists_ShouldThrowException() {
        Long eventId = 999L;
        CreateFeedbackRequest request = new CreateFeedbackRequest();

        when(eventRepository.existsById(eventId)).thenReturn(false);

        assertThrows(EventNotFoundException.class,
                () -> feedbackService.createFeedback(eventId, request));
    }

    @Test
    void getEventSentimentSummary_ShouldCalculateCorrectPercentages() {
        Long eventId = 1L;
        List<Feedback> feedbacks = Arrays.asList(
                createFeedback(1L, "POSITIVE"),
                createFeedback(2L, "POSITIVE"),
                createFeedback(3L, "NEGATIVE"),
                createFeedback(4L, "NEUTRAL")
        );

        when(feedbackRepository.findByEventId(eventId)).thenReturn(feedbacks);

        EventSentimentSummary summary = feedbackService.getEventSentimentSummary(eventId);

        assertEquals(4L, summary.getTotalFeedback());
        assertEquals(2L, summary.getPositiveCount());
        assertEquals(1L, summary.getNeutralCount());
        assertEquals(1L, summary.getNegativeCount());
        assertEquals(50.0, summary.getPositivePercentage());
        assertEquals(25.0, summary.getNeutralPercentage());
        assertEquals(25.0, summary.getNegativePercentage());
    }

    private Feedback createFeedback(Long id, String sentiment) {
        Feedback feedback = new Feedback(1L, "user", "content");
        feedback.setId(id);
        feedback.setSentiment(sentiment);
        return feedback;
    }
}