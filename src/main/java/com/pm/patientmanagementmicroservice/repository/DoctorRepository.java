package com.pm.patientmanagementmicroservice.repository;


import com.pm.patientmanagementmicroservice.model.Case;
import com.pm.patientmanagementmicroservice.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

}
