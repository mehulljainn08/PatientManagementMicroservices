package com.pm.patientmanagementmicroservice.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DoctorWithCases {

    private UUID id;
    private String name;
    private String specialization;
    private String phoneNumber;
    private List<CaseSummaryDTO> cases;
}
