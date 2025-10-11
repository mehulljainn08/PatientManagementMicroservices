package com.pm.patientmanagementmicroservice.dto;

import com.pm.patientmanagementmicroservice.model.Patient;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;


@Data
public class PatientDTO {

    private UUID Id;
    private String name;
    private String email;
    private LocalDate dateOfBirth;
    private String gender;
    private String Address;
    private String phoneNumber;



}
