package com.example.project;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class Nurse {
    private String name;
    private int staffID;
    private String password;
    private Doctor doctor;
    private int doctorID;


    public Nurse() {
        this.name = null;
        this.staffID = 0;
        this.password = null;
        doctor = null;
    }
    public Nurse(String name, int staffID, String password, Doctor doctor) {
        this.name = name;
        this.staffID = staffID;
        this.password = password;
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    public int getStaffID() {
        return staffID;
    }

    public String getPassword()
    {
        return password;
    }

    public Doctor getDoctors()
    {
        return doctor;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setStaffID(int id)
    {
        this.staffID = id;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int id) {
        this.doctorID = id;
    }

    public Nurse getNurseObject(int id) throws SQLException {
        String sql = "SELECT doctorID, nurseID, password, name FROM Nurse WHERE nurseID = ?";

        Nurse nu = new Nurse();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            nu.setName(rs.getString("name"));
            nu.setPassword(rs.getString("password"));
            nu.setStaffID(rs.getInt("nurseID"));
            nu.setDoctorID(rs.getInt("doctorID"));

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return nu;
    }

    public static int getNurseID(int id) throws SQLException {
        String sql = "SELECT nurseID FROM Nurse WHERE doctorID = ?";

        int nurse = -1;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            nurse = rs.getInt("nurseID");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return nurse;
    }

}
