package com.example.project;

import java.util.ArrayList;

public class VisitSummary {
    private String date;
    private Vitals vitals;
    private String doctorNote;
    private ArrayList<HealthIssue> healthIssues;

    public VisitSummary() {
        date = "";
        vitals = null;
        doctorNote = "";
        healthIssues = new ArrayList<HealthIssue>();
    }
    public VisitSummary(String date, Vitals vitals, String doctorNote, ArrayList<HealthIssue> healthIssues) {
        this.date = date;
        this.vitals = vitals;
        this.doctorNote = doctorNote;
        this.healthIssues = healthIssues;
    }
    public String getDate() {
        return date;
    }
    public String getDoctorNote() {
        return doctorNote;
    }
    public Vitals getVitals() {
        return vitals;
    }
    public ArrayList<HealthIssue> getHealthIssues() {
        return healthIssues;
    }
    public void setDate(String newDate) {
        date = newDate;
    }
    public void setHealthIssues(ArrayList<HealthIssue> newIssues) {
        healthIssues = newIssues;
    }
    public void setDoctorNote(String newNote) {
        doctorNote = newNote;
    }
    public void setVitals(Vitals newVitals) {
        vitals = newVitals;
    }
}
