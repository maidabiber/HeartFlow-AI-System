package com.heartflow.vitals;

import com.heartflow.vitals.dto.VitalSignsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VitalSignsService {

    private final VitalSignsRepository vitalSignsRepository;

    public VitalSigns addVitalSigns(VitalSignsRequest request, Long recordedBy) {
        VitalSigns vitals = new VitalSigns();
        vitals.setPatientId(request.getPatientId());
        vitals.setHeartRate(request.getHeartRate());
        vitals.setBpSystolic(request.getBpSystolic());
        vitals.setBpDiastolic(request.getBpDiastolic());
        vitals.setHrv(request.getHrv());
        vitals.setOxygenSaturation(request.getOxygenSaturation());
        vitals.setBodyTemperature(request.getBodyTemperature());
        vitals.setNotes(request.getNotes());
        vitals.setRecordedBy(recordedBy);
        return vitalSignsRepository.save(vitals);
    }

    public List<VitalSigns> getVitalsByPatient(Long patientId) {
        return vitalSignsRepository.findByPatientIdOrderByRecordedAtDesc(patientId);
    }

    public List<VitalSigns> getLatestVitals(Long patientId) {
        return vitalSignsRepository.findTop5ByPatientIdOrderByRecordedAtDesc(patientId);
    }
}
