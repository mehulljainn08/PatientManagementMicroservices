package com.pm.patientmanagementmicroservice.service;

import com.pm.patientmanagementmicroservice.dto.CaseDTO;
import com.pm.patientmanagementmicroservice.dto.PatientDTO;
import com.pm.patientmanagementmicroservice.dto.PatientRequestDTO;
import com.pm.patientmanagementmicroservice.dto.PatientSearchDTO;
import com.pm.patientmanagementmicroservice.exception.EmailAlreadyExistsException;
import com.pm.patientmanagementmicroservice.exception.ResourceNotFoundException;
import com.pm.patientmanagementmicroservice.mapper.EntityMapper;
import com.pm.patientmanagementmicroservice.model.Case;
import com.pm.patientmanagementmicroservice.model.Patient;
import com.pm.patientmanagementmicroservice.repository.CaseRepository;
import com.pm.patientmanagementmicroservice.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private CaseRepository caseRepository;

    public Page<PatientDTO> getAllPatients(Pageable pageable){
        Page<Patient> patientPage = patientRepository.findAll(pageable);

        return patientPage.map(EntityMapper::toPatientDTO);
    }

    public List<CaseDTO> getCasesByPatientId(UUID patientId){

        List<Case> cases= caseRepository.findByPatientId(patientId);

        return cases.stream().map(EntityMapper::toCaseDTO).toList();
    }


    public PatientDTO getPatientById(UUID patientId) {
        return patientRepository.findById(patientId)
                .map(EntityMapper::toPatientDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + patientId));
    }

    public List<CaseDTO> getActiveCasesByPatientId(UUID patientId){
        List<Case> cases= caseRepository.findByPatientIdAndStatusNot(patientId,Case.Status.CLOSED);

        return cases.stream().map(EntityMapper::toCaseDTO).toList();
    }

    public List<PatientDTO> searchPatients(PatientSearchDTO searchCriteria){

        Patient probe=new Patient();
        probe.setName(searchCriteria.getName());
        probe.setAddress(searchCriteria.getAddress());
        probe.setPhoneNumber(searchCriteria.getPhoneNumber());
        probe.setEmail(searchCriteria.getEmail());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Patient> example = Example.of(probe, matcher);

        return patientRepository.findAll(example).stream()
                .map(EntityMapper::toPatientDTO)
                .collect(Collectors.toList());
    }

    public PatientDTO savePatient(PatientRequestDTO patientRequestDTO){
        if(patientRepository.existsByEmail(patientRequestDTO.getEmail())){
            throw new EmailAlreadyExistsException("A patient with this email already exists");
        }
        Patient savedPatient=patientRepository.save(EntityMapper.reqPatient(patientRequestDTO));
        return EntityMapper.toPatientDTO(savedPatient);
    }

    public void deletePatient(UUID patientId) {
        if (!patientRepository.existsById(patientId)) {
            throw new ResourceNotFoundException("Patient not found with id: " + patientId);
        }
        patientRepository.deleteById(patientId);
    }


    public PatientDTO updatePatient(UUID patientId, PatientRequestDTO patientDetails){
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);


        if (optionalPatient.isPresent()) {

            Patient existingPatient = optionalPatient.get();
            if(patientDetails.getEmail()!=null && !patientDetails.getEmail().equals(existingPatient.getEmail()) && patientRepository.existsByEmail(patientDetails.getEmail())){
                throw new EmailAlreadyExistsException("This email is already in use by another patient.");
            }


            existingPatient.partialUpdate(patientDetails);
            Patient updatedPatient = patientRepository.save(existingPatient);
            return EntityMapper.toPatientDTO(updatedPatient);
        }
        throw new ResourceNotFoundException("Patient not found");
    }



}
