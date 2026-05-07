package com.hospital.api.patient;

import com.hospital.api.patient.dto.CreatePatientRequest;
import com.hospital.api.patient.dto.PatientResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PatientService {

    private static final Logger log = LoggerFactory.getLogger(PatientService.class);

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public PatientResponse create(CreatePatientRequest request) {
        var patient = new Patient(request.name(), request.cpf());
        var saved = repository.save(patient);
        log.info("patient created: id={} name={} cpf={}", saved.getId(), saved.getName(), saved.getCpf());
        return PatientResponse.from(saved);
    }

    public List<PatientResponse> findAll() {
        var patients = repository.findAll()
                .stream()
                .map(PatientResponse::from)
                .toList();
        log.info("patients listed ({} records)", patients.size());
        return patients;
    }
}
