package com.example.project;

import java.util.ArrayList;

public class Doctor {
    private String name;
    private int staffID;
    private String password;
    private ArrayList<Patient> patients;
    private ArrayList<Nurse> nurses;

    public Doctor() {
        this.name = null;
        this.staffID = 0;
        this.password = null;
        patients = new ArrayList<Patient>();
        nurses = new ArrayList<Nurse>();
    }
    public Doctor(String name, int staffID, String password) {
        this.name = name;
        this.staffID = staffID;
        this.password = password;
        patients = new ArrayList<Patient>();
        nurses = new ArrayList<Nurse>();
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

    public ArrayList<Patient> getPatients()
    {
        return patients;
    }

    public ArrayList<Nurse> getNurses()
    {
        return nurses;
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

    public void addPatient(Patient patient)
    {
        patients.add(patient);
    }

    public void addNurse(Nurse nurse)
    {
        nurses.add(nurse);
    }
}
