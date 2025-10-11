package com.pm.patientmanagementmicroservice.dto;

import com.pm.patientmanagementmicroservice.model.Patient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientRequestDTO {

    @NotBlank
    private String name;

    @Email(message="Enter Valid Email")
    private String email;

    @NotBlank
    private String address;


    private LocalDate dateOfBirth;


    private Patient.Gender gender;

    @NotBlank
    private String phoneNumber;
}
