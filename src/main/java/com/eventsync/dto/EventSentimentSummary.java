package com.eventsync.dto;

public class EventSentimentSummary {

    private Long eventId;
    private long totalFeedback;
    private long positiveCount;
    private long neutralCount;
    private long negativeCount;
    private double positivePercentage;
    private double neutralPercentage;
    private double negativePercentage;

    public EventSentimentSummary() {}

    public EventSentimentSummary(Long eventId, long totalFeedback, long positiveCount,
                                 long neutralCount, long negativeCount, double positivePercentage,
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

    // Getters and setters
    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public long getTotalFeedback() { return totalFeedback; }
    public void setTotalFeedback(long totalFeedback) { this.totalFeedback = totalFeedback; }

    public long getPositiveCount() { return positiveCount; }
    public void setPositiveCount(long positiveCount) { this.positiveCount = positiveCount; }

    public long getNeutralCount() { return neutralCount; }
    public void setNeutralCount(long neutralCount) { this.neutralCount = neutralCount; }

    public long getNegativeCount() { return negativeCount; }
    public void setNegativeCount(long negativeCount) { this.negativeCount = negativeCount; }

    public double getPositivePercentage() { return positivePercentage; }
    public void setPositivePercentage(double positivePercentage) { this.positivePercentage = positivePercentage; }

    public double getNeutralPercentage() { return neutralPercentage; }
    public void setNeutralPercentage(double neutralPercentage) { this.neutralPercentage = neutralPercentage; }

    public double getNegativePercentage() { return negativePercentage; }
    public void setNegativePercentage(double negativePercentage) { this.negativePercentage = negativePercentage; }

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