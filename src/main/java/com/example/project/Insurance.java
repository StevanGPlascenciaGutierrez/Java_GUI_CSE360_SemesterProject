package com.example.project;

public class Insurance {
    private String name;
    private int phoneNumber;

    public Insurance(){
    }

    public Insurance(String name, Integer phoneNumber){
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(Integer phoneNumber){
        this.phoneNumber = phoneNumber;
    }
}