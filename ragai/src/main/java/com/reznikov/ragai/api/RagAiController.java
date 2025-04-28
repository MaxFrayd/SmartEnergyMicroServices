package com.reznikov.ragai.api;

import com.reznikov.ragai.facade.RagAiFacade;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/rag")
public class RagAiController {
    private final RagAiFacade ragAiFacade;

    public RagAiController(RagAiFacade ragAiFacade) {
        this.ragAiFacade = ragAiFacade;
    }

    /**
     * Downloads CSV from a remote service endpoint, processes, and stores it.
     */
    @PostMapping("/upload-from-service")
    public String uploadCsvFromService() throws IOException {
        return ragAiFacade.uploadCsvFromService();
    }

    /**
     * Queries the CSV data in memory with a simple vector-based approach.
     */
    @GetMapping("/query")
    public String queryCsv(@RequestParam("question") String question) {
        return ragAiFacade.queryCsv(question);
    }

    /**
     * Summarizes the first few rows of the CSV data.
     */
    @GetMapping("/summary")
    public String summarizeCsv() {
        return ragAiFacade.summarizeCsv();
    }
}
