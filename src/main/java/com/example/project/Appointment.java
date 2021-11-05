package com.example.project;

public class Appointment {
    private String time;
    private String date;
    private Doctor doctor;
    private Patient patient;
    private Nurse nurse;

    public Appointment(){
    }

    public Appointment(String time, String date, Doctor doctor, Patient patient, Nurse nurse){
        this.time = time;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.nurse = nurse;
    }
    public String getTime() {
        return time;
    }

    public String getDate(){
        return date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setTime(String newTime) {
        this.time = newTime;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public void setDoctor(Doctor newDoctor) {
        this.doctor = newDoctor;
    }

    public void setPatient(Patient newPatient) {
        this.patient = newPatient;
    }

    public void setNurse(Nurse newNurse) {
        this.nurse = newNurse;
    }
}
