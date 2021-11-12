package com.example.project;

import java.util.ArrayList;

public class Nurse {
    private String name;
    private int staffID;
    private String password;
    private Doctor doctor;


    public Nurse() {
        this.name = null;
        this.staffID = 0;
        this.password = null;
        doctor = null;
    }
    public Nurse(String name, int staffID, String password, Doctor doctor) {
        this.name = name;
        this.staffID = staffID;
        this.password = password;
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getPassword()
    {
        return password;
    }

    public Doctor getDoctors()
    {
        return doctor;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setStaffID(int id)
    {
        this.staffID = id;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
