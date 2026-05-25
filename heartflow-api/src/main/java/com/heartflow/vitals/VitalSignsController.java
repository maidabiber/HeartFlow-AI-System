package com.heartflow.vitals;

import com.heartflow.vitals.dto.VitalSignsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/vitals")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class VitalSignsController {

    private final VitalSignsService vitalSignsService;

    @PostMapping
    public ResponseEntity<VitalSigns> addVitalSigns(@RequestBody VitalSignsRequest request) {
        return ResponseEntity.ok(vitalSignsService.addVitalSigns(request, 1L));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VitalSigns>> getVitalsByPatient(@PathVariable Long patientId) {
        return ResponseEntity.ok(vitalSignsService.getVitalsByPatient(patientId));
    }

    @GetMapping("/patient/{patientId}/latest")
    public ResponseEntity<List<VitalSigns>> getLatestVitals(@PathVariable Long patientId) {
        return ResponseEntity.ok(vitalSignsService.getLatestVitals(patientId));
    }
}
