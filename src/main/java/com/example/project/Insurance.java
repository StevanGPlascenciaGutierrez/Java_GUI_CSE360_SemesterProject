package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.project.Connect.conn;

public class Insurance {
    private String name;
    private int phoneNumber;
    private String address;
    private int memberID;

    public Insurance(){
    }

    public Insurance(String name, Integer phoneNumber, String address, int memberID){
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.memberID = memberID;
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

    public int getMember() {
        return memberID;
    }

    public void setMember(int memberID) {
        this.memberID = memberID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void insert(Insurance ins, int id){
        String sql = "INSERT INTO INSURANCE(name, phoneNumber, patientID, memberID, description) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ins.getName());
            pstmt.setInt(2, ins.getPhoneNumber());
            pstmt.setInt(3, id);
            pstmt.setInt(4, ins.getMember());
            pstmt.setString(5, ins.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}