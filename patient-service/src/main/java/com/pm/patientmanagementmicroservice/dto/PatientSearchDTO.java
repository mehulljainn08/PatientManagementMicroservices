package com.pm.patientmanagementmicroservice.dto;


import lombok.Data;

@Data
public class PatientSearchDTO {
    private String name;
    private String email;
    private String address;
    private String phoneNumber;
}
