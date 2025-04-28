package com.reznikov.ragai.facade;

import com.reznikov.ragai.services.CsvProcessingService;
import com.reznikov.ragai.services.VectorStoreService;
import com.reznikov.ragai.services.client.PaymentRestTemplateClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RagAiFacade {

    private final CsvProcessingService csvService;
    private final VectorStoreService vectorStoreService;
    private final PaymentRestTemplateClient restTemplate;

    // Holds CSV data in memory once downloaded/parsed
    private List<String[]> csvData;

    public RagAiFacade(
            CsvProcessingService csvService,
            VectorStoreService vectorStoreService,
            PaymentRestTemplateClient restTemplate
    ) {
        this.csvService = csvService;
        this.vectorStoreService = vectorStoreService;
        this.restTemplate = restTemplate;
    }

    /**
     * 1. Download the CSV from another microservice.
     * 2. Parse and store it locally.
     */
    public String uploadCsvFromService() throws IOException {
        //this.csvData = csvService.extractDataFromCsv(filePath);
        String data =  new String(restTemplate.getPayments()) ;
        System.out.println(data);
        this.csvData = Arrays.stream(data.split("\n")).map(line->line.split(",")).collect(Collectors.toList());
        return "CSV file downloaded from service, processed, and data stored.";
    }

    /**
     * Run a vector-based query over the CSV data.
     */
    public String queryCsv(String question) {
        if (this.csvData == null || this.csvData.isEmpty()) {
            return "No CSV data available. Please upload first.";
        }
        return vectorStoreService.query(question, this.csvData);
    }

    /**
     * Summarize the first few rows of CSV data.
     */
    public String summarizeCsv() {
        if (this.csvData == null || this.csvData.isEmpty()) {
            return "No CSV data available. Please upload first.";
        }
        return csvService.summarizeData(this.csvData, 3);
    }
}