package com.pm.patientmanagementmicroservice.service;

import com.pm.patientmanagementmicroservice.repository.CaseRepository;
import com.pm.patientmanagementmicroservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CaseRepository caseRepository;


}
