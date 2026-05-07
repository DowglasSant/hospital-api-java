package com.hospital.api.patient.dto;

import com.hospital.api.patient.Patient;

public record PatientResponse(Long id, String name, String cpf) {

    public static PatientResponse from(Patient patient) {
        return new PatientResponse(patient.getId(), patient.getName(), patient.getCpf());
    }
}
