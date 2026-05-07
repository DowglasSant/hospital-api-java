package com.hospital.api.patient.dto;

import jakarta.validation.constraints.NotBlank;

public record CreatePatientRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "cpf is required")
        String cpf
) {}
