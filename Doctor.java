package com.example.javafx.Helpers;

public class Doctor {
    String name,degree,experience;

    public Doctor(String name, String degree, String experience ) {
        this.name = name;
        this.degree = degree;
        this.experience = experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
