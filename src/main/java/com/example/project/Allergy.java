package com.example.project;

public class Allergy {
    private String type;
    private String description;

    public Allergy(){
    }

    public Allergy(String type, String description){
        this.type = type;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDescription(String newDescription){
        this.description = newDescription;
    }
}
