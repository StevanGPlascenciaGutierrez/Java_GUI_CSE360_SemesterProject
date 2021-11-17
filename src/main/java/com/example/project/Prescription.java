package com.example.project;

import javafx.beans.property.ReadOnlySetProperty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

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

    public void insert(Prescription pre, int id){
        String sql = "INSERT INTO PRESCRIPTION(name, startDate, endDate, description, dosage, patientID) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pre.getName());
            pstmt.setString(2, pre.getStartDate());
            pstmt.setString(3, pre.getEndDate());
            pstmt.setString(4, pre.getDescription());
            pstmt.setDouble(5, pre.getDosage());
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void delete(String description, String name){
        String sql = "Delete from PRESCRIPTION where name = ? AND description = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<Prescription> select(int id) {
        String sql = "SELECT * FROM PRESCRIPTION WHERE patientID = ?";
        ArrayList<Prescription> arr = new ArrayList<>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                arr.add(new Prescription(rs.getString("name"), rs.getString("startDate"), rs.getString("endDate"), rs.getString("description"), rs.getDouble("dosage")));
            }

            return arr;
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
