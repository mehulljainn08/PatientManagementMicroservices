package com.pm.patientmanagementmicroservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
@Table(name="case_record")
public class Case {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable=false)
    private Patient patient;

    @NotNull
    private String description;

    public enum Status { OPEN, IN_PROGRESS, CLOSED }

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor assignedDoctor;
}
