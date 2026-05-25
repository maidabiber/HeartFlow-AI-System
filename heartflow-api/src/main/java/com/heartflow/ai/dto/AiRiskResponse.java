package com.heartflow.ai.dto;

import lombok.Data;
import java.util.List;

@Data
public class AiRiskResponse {
    private String riskLevel;             // "NIZAK", "UMJEREN", "VISOK", "VRLO_VISOK"
    private Integer riskScore;            // 0-100
    private List<String> riskFactors;     // lista faktora rizika
    private List<String> recommendations; // preporuke
    private String summary;               // sažetak za ljekara
}