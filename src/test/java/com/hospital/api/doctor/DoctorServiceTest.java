package com.hospital.api.doctor;

import com.hospital.api.doctor.dto.CreateDoctorRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    DoctorRepository repository;

    @InjectMocks
    DoctorService service;

    @Test
    void create_savesAndReturnsResponse() {
        var doctor = new Doctor("Dr. House", "CRM/SP 123456");
        when(repository.save(any())).thenReturn(doctor);

        var response = service.create(new CreateDoctorRequest("Dr. House", "CRM/SP 123456"));

        assertThat(response.name()).isEqualTo("Dr. House");
        assertThat(response.crm()).isEqualTo("CRM/SP 123456");
        verify(repository).save(any(Doctor.class));
    }

    @Test
    void findAll_returnsAllDoctors() {
        when(repository.findAll()).thenReturn(List.of(
                new Doctor("Dr. House", "CRM/SP 123456"),
                new Doctor("Dra. Grey", "CRM/SP 654321")
        ));

        var result = service.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("Dr. House");
    }
}
