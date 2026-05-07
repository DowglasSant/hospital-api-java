package com.hospital.api.doctor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.api.doctor.dto.CreateDoctorRequest;
import com.hospital.api.doctor.dto.DoctorResponse;
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

@WebMvcTest(DoctorController.class)
class DoctorControllerTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    DoctorService service;

    @Test
    void createDoctor_returns201() throws Exception {
        var request = new CreateDoctorRequest("Dr. House", "CRM/SP 123456");
        var response = new DoctorResponse(1L, "Dr. House", "CRM/SP 123456");

        when(service.create(any())).thenReturn(response);

        mvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Dr. House"))
                .andExpect(jsonPath("$.crm").value("CRM/SP 123456"));
    }

    @Test
    void createDoctor_withBlankCrm_returns400() throws Exception {
        var request = new CreateDoctorRequest("Dr. House", "");

        mvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("validation failed"));
    }

    @Test
    void listDoctors_returns200() throws Exception {
        var doctors = List.of(
                new DoctorResponse(1L, "Dr. House", "CRM/SP 123456"),
                new DoctorResponse(2L, "Dra. Grey", "CRM/SP 654321")
        );

        when(service.findAll()).thenReturn(doctors);

        mvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Dr. House"));
    }
}
