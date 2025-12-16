package com.example.javafx.Helpers;

import java.time.LocalDate;

public class AppointMent {
    String name,age,gender,phone;
    String date;
    String docName;

    public AppointMent(String name, String gender, String age, String phone,String dr, String date) {
        this.name = name;
        this.age = age;
        this.docName=dr;
        this.gender = gender;
        this.phone = phone;
        this.date = date;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
