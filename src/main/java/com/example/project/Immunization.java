package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.project.Connect.conn;

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

    public static void insert(Immunization imm, int id){
        String sql = "INSERT INTO IMMUNIZATION(type, date, description, patientID) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, imm.getType());
            pstmt.setString(2, imm.getDate());
            pstmt.setString(3, imm.getDescription());
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
