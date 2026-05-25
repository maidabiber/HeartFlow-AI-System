package com.heartflow.ai;

import com.heartflow.ai.dto.AiRiskRequest;
import com.heartflow.ai.dto.AiRiskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/risk-analysis")
    public ResponseEntity<AiRiskResponse> analyzeRisk(@RequestBody AiRiskRequest request) {
        return ResponseEntity.ok(aiService.analyzeRisk(request));
    }
}