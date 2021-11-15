package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    
    public static void insert(String Name, String Address, int PhoneNumber){
        String sql = "INSERT INTO PATIENT(name, address, phoneNumber) VALUES(?,?,?)";
        try (Connection conn = Connect.conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Name);
            pstmt.setString(2, Address);
            pstmt.setInt(3, PhoneNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
