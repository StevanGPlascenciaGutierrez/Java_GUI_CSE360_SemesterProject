package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    
    public static void insert(String Name, int PhoneNumber){
        String sql = "INSERT INTO PATIENT(name, phoneNumber) VALUES(?,?)";
        try (Connection conn = Connect.conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Name);
            pstmt.setInt(2, PhoneNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void update(String name, int phoneNumber){
        String sql = "UPDATE PATIENT SET name = ? " + "phone number = ? ";
        
        try(Connection conn = Connect.conn;
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            
            pstmt.setString(1, name);
            pstmt.setInt(2, phoneNumber);
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
