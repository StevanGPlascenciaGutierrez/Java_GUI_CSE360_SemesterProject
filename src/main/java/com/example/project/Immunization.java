package com.example.project;

public class Immunization {

    private String type;
    private String date;
    private String description;

    public Immunization(){
    }

    public Immunization(String type, String date, String description){
        this.type = type;
        this.date = date;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }

}
