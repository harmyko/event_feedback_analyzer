package com.eventsync.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SentimentServiceTest {

    @InjectMocks
    private SentimentService sentimentService;

    @Test
    void analyzeSentiment_WhenTextIsNull_ShouldReturnUnknown() {
        String result = sentimentService.analyzeSentiment(null);

        assertEquals("UNKNOWN", result);
    }

    @Test
    void analyzeSentiment_WhenTextIsEmpty_ShouldReturnUnknown() {
        String result = sentimentService.analyzeSentiment("");

        assertEquals("UNKNOWN", result);
    }

    @Test
    void analyzeSentiment_WhenNoApiToken_ShouldReturnUnknown() {
        ReflectionTestUtils.setField(sentimentService, "apiToken", "");

        String result = sentimentService.analyzeSentiment("This is a test");

        assertEquals("UNKNOWN", result);
    }
}