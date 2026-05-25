package com.heartflow.risk;

import com.heartflow.risk.dto.RiskResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/risk")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RiskController {

    private final RiskCalculatorService riskCalculatorService;

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<RiskResponse> calculateRisk(@PathVariable Long patientId) {
        return ResponseEntity.ok(riskCalculatorService.calculateRisk(patientId));
    }
}
