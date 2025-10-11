package com.pm.patientmanagementmicroservice.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class CaseSummaryDTO {

    private UUID id;
    private String description;
    private String status;
    private UUID patientId;



}
