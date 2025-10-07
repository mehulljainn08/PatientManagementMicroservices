package com.pm.patientmanagementmicroservice.model;

import com.pm.patientmanagementmicroservice.dto.PatientDTO;
import com.pm.patientmanagementmicroservice.dto.PatientRequestDTO;
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
    @GeneratedValue(strategy = GenerationType.UUID)
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

    public void partialUpdate(PatientRequestDTO dto) {
        if (dto.getName() != null) {
            this.setName(dto.getName());
        }

        if (dto.getEmail() != null) {
            this.setEmail(dto.getEmail());
        }

        if (dto.getPhoneNumber() != null) {
            this.setPhoneNumber(dto.getPhoneNumber());
        }

        if (dto.getDateOfBirth() != null) {
            this.setDateOfBirth(dto.getDateOfBirth());
        }

        if (dto.getGender() != null) {
            this.setGender(Patient.Gender.valueOf(dto.getGender().toString().toUpperCase()));
        }
        if(dto.getAddress() != null) {
            this.setAddress(dto.getAddress());
        }
    }


}
