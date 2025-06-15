package com.eventsync.dto;

import com.eventsync.model.Feedback;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class FeedbackResponse {

    private Long id;
    private Long eventId;
    private String username;
    private String content;
    private String sentiment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    public FeedbackResponse() {}

    public FeedbackResponse(Long id, Long eventId, String username, String content, String sentiment, LocalDateTime createdAt) {
        this.id = id;
        this.eventId = eventId;
        this.username = username;
        this.content = content;
        this.sentiment = sentiment;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "FeedbackResponse{" +
                "id=" + id +
                ", eventId=" + eventId +
                ", username='" + username +
                ", content='" + content +
                ", createdAt=" + createdAt +
                '}';
    }
}
