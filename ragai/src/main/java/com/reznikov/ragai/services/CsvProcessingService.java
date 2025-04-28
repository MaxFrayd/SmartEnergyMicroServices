package com.reznikov.ragai.services;

import org.springframework.stereotype.Service;

import java.io.*;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CsvProcessingService {

    /**
     * Reads the CSV file and returns all rows as a list of String[].
     */
    public List<String[]> extractDataFromCsv(byte[] data) throws IOException {
//        try (CSVReader reader = new CSVReader(data)){
//            return reader.readAll();
//        } catch (CsvException e) {
//            throw new RuntimeException(e);
//        }
        return null;
    }

    /**
     * Utility method to download the file from a remote microservice.
     */
//    public String downloadFileFromService(String localFilePath) throws IOException {
//
//        Resource resource = response.getBody();
//        if (resource != null) {
//            Path targetPath = new File(localFilePath).toPath();
//            Files.copy(resource.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
//            return targetPath.toString();
//        } else {
//            throw new FileNotFoundException("File not found at: " + serviceUrl);
//        }
//    }

    /**
     * Summarizes the first N rows of the CSV by concatenating them into a string.
     */
    public String summarizeData(List<String[]> csvData, int numberOfRows) {
        return csvData.stream()
                .limit(numberOfRows)
                .map(row -> String.join(",", row))
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
