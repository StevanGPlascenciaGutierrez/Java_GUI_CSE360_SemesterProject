package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.project.Connect.conn;

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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(Integer phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public void insert(Pharmacy pharm, int id){
        String sql = "INSERT INTO Pharamacy(name, address, phoneNumber, patientID) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pharm.getName());
            pstmt.setString(2, pharm.getAddress());
            pstmt.setInt(3, pharm.getPhoneNumber());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}