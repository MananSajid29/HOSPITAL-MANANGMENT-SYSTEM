package com.example.javafx.Helpers;

import java.time.LocalDate;

public class Patient {

    private String name;
    private String gender;
    private int age;
    private String ward;
    private LocalDate admitDate;
    private LocalDate dischargeDate;

    public Patient(String name, String gender, int age, String ward, LocalDate admitDate) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.ward = ward;
        this.admitDate = admitDate;
        this.dischargeDate = null;
    }

    // Getters
    public String getName() { return name; }
    public String getGender() { return gender; }
    public int getAge() { return age; }
    public String getWard() { return ward; }
    public LocalDate getAdmitDate() { return admitDate; }
    public LocalDate getDischargeDate() { return dischargeDate; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setGender(String gender) { this.gender = gender; }
    public void setAge(int age) { this.age = age; }
    public void setWard(String ward) { this.ward = ward; }
    public void setAdmitDate(LocalDate admitDate) { this.admitDate = admitDate; }
    public void setDischargeDate(LocalDate dischargeDate) { this.dischargeDate = dischargeDate; }
}
