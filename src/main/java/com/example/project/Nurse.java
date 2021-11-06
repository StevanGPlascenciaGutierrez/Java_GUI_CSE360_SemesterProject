package com.example.project;

import java.util.ArrayList;

public class Nurse {
    private String name;
    private int staffID;
    private String password;
    private ArrayList<Doctor> doctors;


    public Nurse() {
        this.name = null;
        this.staffID = 0;
        this.password = null;
        doctors = new ArrayList<Doctor>();
    }
    public Nurse(String name, int staffID, String password) {
        this.name = name;
        this.staffID = staffID;
        this.password = password;
        doctors = new ArrayList<Doctor>();
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

    public ArrayList<Doctor> getDoctors()
    {
        return doctors;
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

    public void addDoctor(Doctor doctor)
    {
        doctors.add(doctor);
    }
}
