package com.example.project;

public class Prescription {
    private String name;
    private String startDate;
    private String endDate;
    private String description;
    private double dosage;

    public Prescription(){
    }

    public Prescription(String name, String startDate, String endDate, String description, double dosage){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.dosage = dosage;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getDescription(){
        return description;
    }

    public double getDosage() {
        return dosage;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setStartDate(String newStartDate) {
        this.startDate = newStartDate;
    }

    public void setEndDate(String newEndDate) {
        this.endDate = newEndDate;
    }

    public void setDescription(String newDescription) {
        this.description = newDescription;
    }

    public void setDosage(double newDosage) {
        this.dosage = newDosage;
    }
}
