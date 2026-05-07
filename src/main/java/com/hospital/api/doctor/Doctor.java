package com.hospital.api.doctor;

import jakarta.persistence.*;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String crm;

    protected Doctor() {}

    public Doctor(String name, String crm) {
        this.name = name;
        this.crm = crm;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCrm() { return crm; }
}
