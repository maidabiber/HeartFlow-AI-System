package com.heartflow.patient.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.heartflow.patient.Gender;

@Data
public class PatientResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
}
