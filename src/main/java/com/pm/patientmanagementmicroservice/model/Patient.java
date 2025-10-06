package com.pm.patientmanagementmicroservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID patientId;
    @NotNull
    private String name;

    @Email
    @Column(unique=true)
    @NotNull
    private String email;


    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private String address;

    @NotNull
    private String phoneNumber;

    public enum Gender {
        MALE,FEMALE,OTHER
    }

    @Enumerated(EnumType.STRING)
    private Gender gender;



}
