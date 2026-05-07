package com.hospital.api.doctor.dto;

import com.hospital.api.doctor.Doctor;

public record DoctorResponse(Long id, String name, String crm) {

    public static DoctorResponse from(Doctor doctor) {
        return new DoctorResponse(doctor.getId(), doctor.getName(), doctor.getCrm());
    }
}
