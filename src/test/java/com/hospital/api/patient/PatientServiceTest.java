package com.hospital.api.patient;

import com.hospital.api.patient.dto.CreatePatientRequest;
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
class PatientServiceTest {

    @Mock
    PatientRepository repository;

    @InjectMocks
    PatientService service;

    @Test
    void create_savesAndReturnsResponse() {
        var patient = new Patient("João Silva", "123.456.789-00");
        when(repository.save(any())).thenReturn(patient);

        var response = service.create(new CreatePatientRequest("João Silva", "123.456.789-00"));

        assertThat(response.name()).isEqualTo("João Silva");
        assertThat(response.cpf()).isEqualTo("123.456.789-00");
        verify(repository).save(any(Patient.class));
    }

    @Test
    void findAll_returnsAllPatients() {
        when(repository.findAll()).thenReturn(List.of(
                new Patient("João Silva", "111.111.111-11"),
                new Patient("Maria Souza", "222.222.222-22")
        ));

        var result = service.findAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).name()).isEqualTo("João Silva");
    }
}
