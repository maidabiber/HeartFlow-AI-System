package com.heartflow.vitals.dto;

import lombok.Data;

@Data
public class VitalSignsRequest {
    private Long patientId;
    private Integer heartRate;
    private Integer bpSystolic;
    private Integer bpDiastolic;
    private Double hrv;
    private Double oxygenSaturation;
    private Double bodyTemperature;
    private String notes;
}
