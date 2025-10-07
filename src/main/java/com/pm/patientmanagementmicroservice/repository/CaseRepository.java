package com.pm.patientmanagementmicroservice.repository;

import com.pm.patientmanagementmicroservice.model.Case;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CaseRepository extends JpaRepository<Case, UUID> {
    List<Case> findByPatientId(UUID patientId);
    List<Case> findByPatientIdAndStatusNot(UUID patientId, Case.Status status);
    List<Case> findByDoctorId(UUID doctorId);

}
