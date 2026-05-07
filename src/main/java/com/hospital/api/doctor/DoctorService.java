package com.hospital.api.doctor;

import com.hospital.api.doctor.dto.CreateDoctorRequest;
import com.hospital.api.doctor.dto.DoctorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DoctorService {

    private static final Logger log = LoggerFactory.getLogger(DoctorService.class);

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public DoctorResponse create(CreateDoctorRequest request) {
        var doctor = new Doctor(request.name(), request.crm());
        var saved = repository.save(doctor);
        log.info("doctor created: id={} name={} crm={}", saved.getId(), saved.getName(), saved.getCrm());
        return DoctorResponse.from(saved);
    }

    public List<DoctorResponse> findAll() {
        var doctors = repository.findAll()
                .stream()
                .map(DoctorResponse::from)
                .toList();
        log.info("doctors listed ({} records)", doctors.size());
        return doctors;
    }
}
