package com.hospital.api.doctor.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateDoctorRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "crm is required")
        String crm
) {}
