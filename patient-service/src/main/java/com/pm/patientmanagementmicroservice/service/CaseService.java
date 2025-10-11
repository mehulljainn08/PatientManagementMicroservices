package com.pm.patientmanagementmicroservice.service;

import com.pm.patientmanagementmicroservice.dto.CaseDTO;
import com.pm.patientmanagementmicroservice.exception.ResourceNotFoundException;
import com.pm.patientmanagementmicroservice.mapper.EntityMapper;
import com.pm.patientmanagementmicroservice.model.Case;
import com.pm.patientmanagementmicroservice.model.Doctor;
import com.pm.patientmanagementmicroservice.model.Patient;
import com.pm.patientmanagementmicroservice.repository.CaseRepository;

import com.pm.patientmanagementmicroservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CaseService {


    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private PatientRepository patientRepository;



    public List<CaseDTO> getAllCases(){
        List<Case> cases = caseRepository.findAll();
        return cases.stream().map(EntityMapper::toCaseDTO).collect(Collectors.toList());
    }

    public CaseDTO getCaseById(UUID caseId){

        Optional<Case> optionalCase = caseRepository.findById(caseId);
        if(optionalCase.isPresent()){
            return EntityMapper.toCaseDTO(optionalCase.get());
        }
        throw new ResourceNotFoundException("Case not found");
    }

    public CaseDTO getCasesByPatientId(UUID patientId){
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if(optionalPatient.isPresent()){
            List<Case> cases = caseRepository.findByPatientId(patientId);
            return cases.stream().map(EntityMapper::toCaseDTO).collect(Collectors.toList()).get(0);
        }
        throw new ResourceNotFoundException("Patient not found");
    }




}
