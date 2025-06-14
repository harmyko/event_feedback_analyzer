package com.eventsync.service;

import com.eventsync.dto.FeedbackResponse;
import com.eventsync.dto.CreateFeedbackRequest;
import com.eventsync.model.Feedback;
import com.eventsync.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final SentimentService sentimentService;

    @Autowired
    public FeedbackService(FeedbackRepository feedbackRepository, SentimentService sentimentService) {
        this.feedbackRepository = feedbackRepository;
        this.sentimentService = sentimentService;
    }

    public FeedbackResponse createFeedback(Long eventId, CreateFeedbackRequest request) {
        Feedback feedback = new Feedback(
                eventId, request.getUsername(), request.getContent()
        );

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


}
