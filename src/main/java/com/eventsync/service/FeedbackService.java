package com.eventsync.service;

import com.eventsync.dto.EventResponse;
import com.eventsync.dto.EventSentimentSummary;
import com.eventsync.dto.FeedbackResponse;
import com.eventsync.dto.CreateFeedbackRequest;
import com.eventsync.model.Feedback;
import com.eventsync.repository.EventRepository;
import com.eventsync.repository.FeedbackRepository;
import com.eventsync.exception.EventNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final EventRepository eventRepository;
    private final SentimentService sentimentService;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, SentimentService sentimentService, EventRepository eventRepository) {
        this.feedbackRepository = feedbackRepository;
        this.eventRepository = eventRepository;
        this.sentimentService = sentimentService;
    }

    public FeedbackResponse createFeedback(Long eventId, CreateFeedbackRequest request) {
        if(!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Event with provided ID does not exist");
        }

        Feedback feedback = new Feedback(
                eventId, request.getUsername(), request.getContent()
        );

        // TODO: analyze sentiment asynchronously for better user experience
        String sentiment = sentimentService.analyzeSentiment(feedback.getContent());
        feedback.setSentiment(sentiment);

        Feedback saved = feedbackRepository.save(feedback);

        return convertToResponse(saved);
    }

    public List<FeedbackResponse> getEventFeedback(Long eventId) {
        List<Feedback> feedback = feedbackRepository.findByEventId(eventId);
        return feedback.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private FeedbackResponse convertToResponse(Feedback saved) {
        return new FeedbackResponse(
                saved.getId(),
                saved.getEventId(),
                saved.getUsername(),
                saved.getContent(),
                saved.getSentiment(),
                saved.getCreatedAt()
        );
    }


    public EventSentimentSummary getEventSentimentSummary(Long eventId) {
        List<Feedback> feedbacks = feedbackRepository.findByEventId(eventId);

        Map<String, Long> sentimentCount = feedbacks.stream()
                .collect(Collectors.groupingBy(
                        feedback -> feedback.getSentiment() != null ? feedback.getSentiment() : "NEUTRAL",
                        Collectors.counting()
                ));

        Long totalFeedback = (long) feedbacks.size();
        Long positiveCount = sentimentCount.getOrDefault("POSITIVE", 0L);
        Long neutralCount = sentimentCount.getOrDefault("NEUTRAL", 0L);
        Long negativeCount = sentimentCount.getOrDefault("NEGATIVE", 0L);

        return new EventSentimentSummary(
                eventId,
                totalFeedback,
                positiveCount,
                neutralCount,
                negativeCount,
                calculatePercentage(positiveCount, totalFeedback),
                calculatePercentage(neutralCount, totalFeedback),
                calculatePercentage(negativeCount, totalFeedback)
        );
    }

    private double calculatePercentage(Long positiveCount, Long totalFeedback) {
        if (totalFeedback == 0) {
            return 0.0;
        }
        return Math.round((positiveCount * 100.0 / totalFeedback) * 100.0) / 100.0;
    }
}
