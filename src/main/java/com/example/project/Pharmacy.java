package com.example.project;

public class Pharmacy {

    private String name;
    private String address;
    private int phoneNumber;

    public Pharmacy(){
    }

    public Pharmacy(String name, String address, int phoneNumber){
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String Address) {
        this.address = address;
    }

    public void setDescription(Integer phoneNumber){
        this.phoneNumber = phoneNumber;
    }

}