package com.heartflow.risk;

import com.heartflow.vitals.VitalSigns;
import com.heartflow.vitals.VitalSignsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import com.heartflow.risk.dto.RiskResponse;

@Service
@RequiredArgsConstructor
public class RiskCalculatorService {

    private final VitalSignsRepository vitalSignsRepository;

    public RiskResponse calculateRisk(Long patientId) {
        List<VitalSigns> recentVitals = vitalSignsRepository
                .findTop5ByPatientIdOrderByRecordedAtDesc(patientId);

        if (recentVitals.isEmpty()) {
            return new RiskResponse(0.0, "UNKNOWN", "No vital signs data available.", patientId);
        }

        VitalSigns latest = recentVitals.get(0);
        double score = 0.0;
        StringBuilder factors = new StringBuilder();

        // Heart rate scoring (normal: 60-100)
        if (latest.getHeartRate() != null) {
            if (latest.getHeartRate() > 100) {
                score += 25;
                factors.append("High heart rate. ");
            } else if (latest.getHeartRate() < 60) {
                score += 15;
                factors.append("Low heart rate. ");
            }
        }

        // Blood pressure scoring (normal systolic: 90-120)
        if (latest.getBpSystolic() != null) {
            if (latest.getBpSystolic() > 140) {
                score += 30;
                factors.append("High systolic blood pressure. ");
            } else if (latest.getBpSystolic() > 120) {
                score += 15;
                factors.append("Elevated systolic blood pressure. ");
            }
        }

        // Diastolic pressure (normal: 60-80)
        if (latest.getBpDiastolic() != null) {
            if (latest.getBpDiastolic() > 90) {
                score += 20;
                factors.append("High diastolic blood pressure. ");
            }
        }

        // HRV scoring (higher HRV = lower risk)
        if (latest.getHrv() != null) {
            if (latest.getHrv() < 20) {
                score += 20;
                factors.append("Low HRV indicates stress. ");
            } else if (latest.getHrv() > 60) {
                score -= 10;
                factors.append("Good HRV. ");
            }
        }

        // Oxygen saturation (normal: 95-100)
        if (latest.getOxygenSaturation() != null) {
            if (latest.getOxygenSaturation() < 95) {
                score += 25;
                factors.append("Low oxygen saturation. ");
            }
        }

        score = Math.max(0, Math.min(100, score));

        String level;
        if (score < 20) level = "LOW";
        else if (score < 40) level = "MODERATE";
        else if (score < 70) level = "HIGH";
        else level = "CRITICAL";

        return new RiskResponse(score, level, factors.toString(), patientId);
    }
}
