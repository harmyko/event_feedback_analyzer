package com.eventsync.dto;

public class EventSentimentSummary {

    private Long eventId;
    private Long totalFeedback;
    private Long positiveCount;
    private Long neutralCount;
    private Long negativeCount;
    private double positivePercentage;
    private double neutralPercentage;
    private double negativePercentage;

    public EventSentimentSummary() {}

    public EventSentimentSummary(Long eventId, Long totalFeedback, Long positiveCount,
                                 Long neutralCount, Long negativeCount, double positivePercentage,
                                 double neutralPercentage, double negativePercentage) {
        this.eventId = eventId;
        this.totalFeedback = totalFeedback;
        this.positiveCount = positiveCount;
        this.neutralCount = neutralCount;
        this.negativeCount = negativeCount;
        this.positivePercentage = positivePercentage;
        this.neutralPercentage = neutralPercentage;
        this.negativePercentage = negativePercentage;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getTotalFeedback() {
        return totalFeedback;
    }

    public void setTotalFeedback(Long totalFeedback) {
        this.totalFeedback = totalFeedback;
    }

    public Long getPositiveCount() {
        return positiveCount;
    }

    public void setPositiveCount(Long positiveCount) {
        this.positiveCount = positiveCount;
    }

    public Long getNeutralCount() {
        return neutralCount;

    }

    public void setNeutralCount(Long neutralCount) {
        this.neutralCount = neutralCount;
    }

    public Long getNegativeCount() {
        return negativeCount;
    }

    public void setNegativeCount(Long negativeCount) {
        this.negativeCount = negativeCount;
    }

    public double getPositivePercentage() {
        return positivePercentage;
    }

    public void setPositivePercentage(double positivePercentage) {
        this.positivePercentage = positivePercentage;
    }

    public double getNeutralPercentage() {
        return neutralPercentage;
    }

    public void setNeutralPercentage(double neutralPercentage) {
        this.neutralPercentage = neutralPercentage;
    }

    public double getNegativePercentage() {
        return negativePercentage;
    }

    public void setNegativePercentage(double negativePercentage) {
        this.negativePercentage = negativePercentage;
    }

    @Override
    public String toString() {
        return "EventSentimentSummary{" +
                "eventId=" + eventId +
                ", totalFeedback=" + totalFeedback +
                ", positiveCount=" + positiveCount +
                ", neutralCount=" + neutralCount +
                ", negativeCount=" + negativeCount +
                ", positivePercentage=" + positivePercentage +
                ", neutralPercentage=" + neutralPercentage +
                ", negativePercentage=" + negativePercentage +
                '}';
    }
}