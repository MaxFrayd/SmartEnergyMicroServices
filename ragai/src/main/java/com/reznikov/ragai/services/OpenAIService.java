package com.reznikov.ragai.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reznikov.ragai.dto.ChatRequestDto;
import com.reznikov.ragai.dto.PaymentAmountDto;
import com.reznikov.ragai.services.client.PaymentRestTemplateClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class OpenAIService {
    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final PaymentRestTemplateClient paymentRestTemplateClient;
    private final ObjectMapper objectMapper;
    private static final String prompt = "Give me total payment amount '";


    public OpenAIService(RestTemplate restTemplate, ObjectMapper objectMapper, PaymentRestTemplateClient paymentRestTemplateClient) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.paymentRestTemplateClient = paymentRestTemplateClient;
    }

    public String getPrediction() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + apiKey);

            // Retrieve your payment data as a String.
            String data = new String(paymentRestTemplateClient.getPayments());

            ChatRequestDto chatRequestDto = new ChatRequestDto("user","you have to analyze our incoming data, and you will send back your answer in Json format ");
            ChatRequestDto chatUserRequestDto = new ChatRequestDto("user",prompt + data);

            ChatRequestDto[] messages = {chatRequestDto, chatUserRequestDto};

            //String messagesJson = objectMapper.writeValueAsString(messages);

            // Build a payload map. Note: include all required parameters, such as "model".
            Map<String, Object> payload = new HashMap<>();
            payload.put("model", "o1-mini"); // or your chosen model model: "gpt-4o",
            payload.put("messages", messages);     // 'prompt' must be defined and not null
            payload.put("max_completion_tokens", 1000);

            // Serialize the payload to a JSON string.
            String requestBody = objectMapper.writeValueAsString(payload);

            HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            JsonNode jsonResponse = objectMapper.readTree(response.getBody());
            System.out.println(response);
            String rawContent =  jsonResponse.get("choices")
                    .get(0)
                    .get("message")
                    .get("content")
                    .asText().trim();
            // Remove markdown code block markers and extra whitespace/newlines.
            String jsonContent = rawContent.replaceAll("^```json\\s*", "")
                    .replaceAll("\\s*```$", "")
                    .trim();

// Now parse the cleaned JSON string into your DTO.
            PaymentAmountDto result = objectMapper.readValue(jsonContent, PaymentAmountDto.class);
            return objectMapper.writeValueAsString(result);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
