package com.reznikov.ragai.services;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class VectorStoreService {

    private final RestTemplate restTemplate;

    // OpenAI endpoint for embeddings
    private static final String OPENAI_EMBEDDING_ENDPOINT = "https://api.openai.com/v1/embeddings";
    // Make sure you have your API key in an environment variable named OPENAI_API_KEY
    private static final String ref = "******";

    public VectorStoreService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves embeddings from OpenAI's text-embedding-ada-002 model.
     *
     * @param text The input text for which to generate embeddings.
     * @return A list of Doubles representing the embedding vector.
     */
    @SuppressWarnings("unchecked")
    public List<Double> getEmbeddings(String text) {
        try {
            // Prepare request headers
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(ref);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare JSON body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "text-embedding-ada-002");
            requestBody.put("input", text);

            // Create an HTTP entity with the headers and body
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // Call the OpenAI embeddings endpoint
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    OPENAI_EMBEDDING_ENDPOINT,
                    requestEntity,
                    Map.class
            );

            // The response body should contain the embedding in response.getBody().get("data")
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                // Extract the "data" array from the response
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) response.getBody().get("data");
                if (dataList != null && !dataList.isEmpty()) {
                    // Each item in "data" contains an "embedding" array
                    Map<String, Object> firstItem = dataList.get(0);
                    List<Double> embedding = (List<Double>) firstItem.get("embedding");
                    return embedding;
                }
            }
        } catch (Exception e) {
            // Logging or handle error as needed
            System.err.println("Error calling OpenAI embeddings endpoint: " + e.getMessage());
        }

        // Fallback if there's an error or no data
        return Collections.emptyList();
    }


    /**
     * Computes the cosine similarity between two vectors.
     *
     * @param vec1 First vector.
     * @param vec2 Second vector.
     * @return Cosine similarity value.
     */
    private double cosineSimilarity(List<Double> vec1, List<Double> vec2) {
        if (vec1.isEmpty() || vec2.isEmpty() || vec1.size() != vec2.size()) {
            return 0.0;
        }
        double dotProduct = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            normA += Math.pow(vec1.get(i), 2);
            normB += Math.pow(vec2.get(i), 2);
        }
        if (normA == 0.0 || normB == 0.0) {
            return 0.0;
        }
        return dotProduct / (Math.sqrt(normA) * Math.sqrt(normB));
    }

    /**
     * Performs a vector search over CSV data.
     * Each CSV row is joined into a single string, and its embedding is computed.
     * The cosine similarity between the question embedding and the row embedding is calculated,
     * and the row with the highest similarity is returned.
     *
     * @param question The user's question.
     * @param csvData  List of CSV rows, where each row is an array of strings.
     * @return The CSV row that best matches the question.
     */
    public String query(String question, List<String[]> csvData) {
        List<Double> questionEmbedding = getEmbeddings(question);
        if (questionEmbedding.isEmpty()) {
            return "Error retrieving question embedding.";
        }

        String bestMatchRow = "No relevant information found.";
        double bestSimilarity = -1.0;

        for (String[] row : csvData) {
            // Convert the CSV row to a single string.
            String rowText = String.join(" ", row);
            List<Double> rowEmbedding = getEmbeddings(rowText);
            if (rowEmbedding.isEmpty()) {
                continue;
            }
            double similarity = cosineSimilarity(questionEmbedding, rowEmbedding);
            if (similarity > bestSimilarity) {
                bestSimilarity = similarity;
                bestMatchRow = rowText;
            }
        }

        return bestMatchRow;
    }


}
