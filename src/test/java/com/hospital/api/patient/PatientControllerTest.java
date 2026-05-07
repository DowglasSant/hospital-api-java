package com.hospital.api.patient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.api.patient.dto.CreatePatientRequest;
import com.hospital.api.patient.dto.PatientResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    PatientService service;

    @Test
    void createPatient_returns201() throws Exception {
        var request = new CreatePatientRequest("João Silva", "123.456.789-00");
        var response = new PatientResponse(1L, "João Silva", "123.456.789-00");

        when(service.create(any())).thenReturn(response);

        mvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("123.456.789-00"));
    }

    @Test
    void createPatient_withBlankName_returns400() throws Exception {
        var request = new CreatePatientRequest("", "123.456.789-00");

        mvc.perform(post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation failed"));
    }

    @Test
    void listPatients_returns200() throws Exception {
        var patients = List.of(
                new PatientResponse(1L, "João Silva", "123.456.789-00"),
                new PatientResponse(2L, "Maria Souza", "987.654.321-00")
        );

        when(service.findAll()).thenReturn(patients);

        mvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("João Silva"));
    }
}
