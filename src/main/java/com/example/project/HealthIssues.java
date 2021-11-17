package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.project.Connect.conn;

public class HealthIssues extends HealthHistory {
    private String name;
    private String description;
    private String date;

    public HealthIssues() {
        name = null;
        description =null;
        date =null;
    }

    public HealthIssues(String name, String description, String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void insert(String Name, String Description, String Date, int id, int num){
        String sql = "INSERT INTO HealthIssue(name, description, date, patientID, visitNumber) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Name);
            pstmt.setString(2, Description);
            pstmt.setString(3, Date);
            pstmt.setInt(4, id);
            pstmt.setInt(5, num);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}