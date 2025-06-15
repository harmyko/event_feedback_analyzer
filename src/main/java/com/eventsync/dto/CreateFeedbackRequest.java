package com.eventsync.dto;

public class CreateFeedbackRequest {

    private Long eventId;
    private String username;
    private String content;

    public CreateFeedbackRequest() {}

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

    @Override
    public String toString() {
        return "CreateFeedbackRequest{" +
                "eventId='" + eventId +
                ", username='" + username +
                ", content='" + content +
                '}';
    }
}
