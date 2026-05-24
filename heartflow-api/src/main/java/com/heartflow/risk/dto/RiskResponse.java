package com.heartflow.risk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RiskResponse {
    private Double score;
    private String level;
    private String factors;
    private Long patientId;
}
