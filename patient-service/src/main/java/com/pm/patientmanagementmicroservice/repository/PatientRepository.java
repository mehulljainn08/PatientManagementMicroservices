package com.pm.patientmanagementmicroservice.repository;

import com.pm.patientmanagementmicroservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    List<Patient> findByNameContainingIgnoreCase(String name);
    boolean existsByEmail(String email);
}
