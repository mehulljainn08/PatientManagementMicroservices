package com.pm.patientmanagementmicroservice.controller;


import com.pm.patientmanagementmicroservice.dto.CaseDTO;
import com.pm.patientmanagementmicroservice.dto.PatientDTO;
import com.pm.patientmanagementmicroservice.dto.PatientRequestDTO;
import com.pm.patientmanagementmicroservice.dto.PatientSearchDTO;
import com.pm.patientmanagementmicroservice.model.Patient;
import com.pm.patientmanagementmicroservice.service.PatientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
@Tag(name="Patient", description="API for managing Patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @GetMapping("all-patient-record")
    @Operation(summary="get patient")
    public ResponseEntity<Page<PatientDTO>> getAllPatients(Pageable pageable){
        return ResponseEntity.ok(patientService.getAllPatients(pageable));
    }

    @PostMapping("/search")
    @Operation(summary="Search Patients with filters")
    public ResponseEntity<List<PatientDTO>> searchPatients(@RequestBody PatientSearchDTO patientSearchDTO){
        return ResponseEntity.ok(patientService.searchPatients(patientSearchDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> findPatientById(@PathVariable UUID id){
        PatientDTO patientDTO=patientService.getPatientById(id);

        if (patientDTO != null) {
            return ResponseEntity.ok(patientDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }




    @GetMapping("/{id}/cases")
    @Operation(summary="Get list of cases for a Patient")
    public ResponseEntity<List<CaseDTO>> getCasesForPatient(@PathVariable UUID id) {

        List<CaseDTO> cases = patientService.getCasesByPatientId(id);
        return ResponseEntity.ok(cases);
    }


    @PostMapping("/create-patient-record")
    @Operation(summary="add a new patient")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientRequestDTO patientRequestDTO) {
        PatientDTO patientToSave = patientService.savePatient(patientRequestDTO);

        return new ResponseEntity<>(patientToSave, HttpStatus.CREATED);
    }


    @PatchMapping("/update-patient-record/{id}")
    @Operation(summary="update an existing patient")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable UUID id, @RequestBody PatientRequestDTO patientDetails) {
        PatientDTO updatedPatient = patientService.updatePatient(id, patientDetails);
        if (updatedPatient != null) {
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete-patient-record/{id}")
    @Operation(summary="Delete Patient Record")
    public ResponseEntity<?> deletePatient(@PathVariable UUID id) {

        try{
            patientService.deletePatient(id);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}
