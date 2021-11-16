package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.project.Connect.conn;

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

    public static void insert(Allergy all, int id){
        String sql = "INSERT INTO ALLERGY(type, description, patientID) VALUES(?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, all.getType());
            pstmt.setString(2, all.getDescription());
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
