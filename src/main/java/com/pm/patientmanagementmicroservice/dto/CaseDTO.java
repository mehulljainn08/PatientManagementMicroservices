package com.pm.patientmanagementmicroservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CaseDTO {

    private UUID id;
    private String description;
    private String status;
    private UUID patientId;
    private UUID doctorId;
}
