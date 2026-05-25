package com.heartflow.ai.dto;

import lombok.Data;

@Data
public class AiRiskRequest {
    private Integer age;
    private String gender;        // "MALE", "FEMALE"

    // Vitalni znaci
    private String bloodPressure; // npr. "120/80"
    private Integer heartRate;
    private Double bmi;

    // Lab nalazi
    private Double cholesterolTotal;
    private Double cholesterolLdl;
    private Double cholesterolHdl;
    private Double triglycerides;
    private Double bloodSugar;

    // Faktori rizika
    private Boolean smoker;
    private Boolean diabetic;
    private Boolean hypertension;
    private Boolean familyHistory;
}