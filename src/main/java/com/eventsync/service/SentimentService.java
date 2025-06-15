package com.eventsync.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class SentimentService {

    private static final Logger logger = LoggerFactory.getLogger(SentimentService.class);
    private static final String HUGGING_FACE_API_URL = "https://api-inference.huggingface.co/models/cardiffnlp/twitter-roberta-base-sentiment";

    @Value("${huggingface.api.token:}")
    private String apiToken;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SentimentService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String analyzeSentiment(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "UNKNOWN";
        }

        if (apiToken.isEmpty()) {
            logger.info("No Hugging Face API token provided");
            return "UNKNOWN";
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            headers.setBearerAuth(apiToken);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("inputs", text);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    HUGGING_FACE_API_URL,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                return parseSentimentResponse(response.getBody());
            } else {
                logger.warn("Hugging Face API returned status: {}", response.getStatusCode());
                return "UNKNOWN";
            }

        } catch (RestClientException e) {
            logger.error("Error calling Hugging Face API: {}", e.getMessage());
            return "UNKNOWN";
        } catch (Exception e) {
            logger.error("Unexpected error during sentiment analysis: {}", e.getMessage());
            return "UNKNOWN";
        }
    }

    private String parseSentimentResponse(String responseBody) {
        try {
            JsonNode jsonResponse = objectMapper.readTree(responseBody);

            if (jsonResponse.isArray() && !jsonResponse.isEmpty()) {
                JsonNode firstResult = jsonResponse.get(0);
                if (firstResult.isArray() && !firstResult.isEmpty()) {
                    JsonNode topResult = firstResult.get(0);
                    String label = topResult.get("label").asText();
                    return mapHuggingFaceLabel(label);
                }
            }

            logger.warn("Unexpected response format from Hugging Face API");
            return "UNKNOWN";

        } catch (Exception e) {
            logger.error("Error parsing sentiment response: {}", e.getMessage());
            return "UNKNOWN";
        }
    }

    private String mapHuggingFaceLabel(String label) {
        // Map Hugging Face labels to our format
        switch (label.toUpperCase()) {
            case "LABEL_0":
            case "NEGATIVE":
                return "NEGATIVE";
            case "LABEL_1":
            case "NEUTRAL":
                return "NEUTRAL";
            case "LABEL_2":
            case "POSITIVE":
                return "POSITIVE";
            default:
                return "NEUTRAL";
        }
    }
}