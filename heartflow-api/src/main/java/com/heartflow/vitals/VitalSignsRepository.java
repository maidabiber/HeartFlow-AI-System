package com.heartflow.vitals;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VitalSignsRepository extends JpaRepository<VitalSigns, Long> {
    List<VitalSigns> findByPatientIdOrderByRecordedAtDesc(Long patientId);
    List<VitalSigns> findTop5ByPatientIdOrderByRecordedAtDesc(Long patientId);
}
