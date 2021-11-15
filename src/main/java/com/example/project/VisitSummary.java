package com.example.project;

import java.sql.*;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class VisitSummary {
    private String date;
    private Vitals vitals;
    private String doctorNote;
    private ArrayList<HealthIssues> healthIssues;
    private int visitNum;

    public VisitSummary() {
        date = "";
        vitals = null;
        doctorNote = "";
        healthIssues = new ArrayList<>();
    }
    public VisitSummary(String date, Vitals vitals, String doctorNote, ArrayList<HealthIssues> healthIssues) {
        this.date = date;
        this.vitals = vitals;
        this.doctorNote = doctorNote;
        this.healthIssues = healthIssues;
    }
    public String getDate() {
        return date;
    }
    public String getDoctorNote() {
        return doctorNote;
    }
    public Vitals getVitals() {
        return vitals;
    }
    public ArrayList<HealthIssues> getHealthIssues() {
        return healthIssues;
    }
    public int getVisitNum(){return visitNum;}
    public void setDate(String newDate) {
        date = newDate;
    }
    public void setHealthIssues(ArrayList<HealthIssues> newIssues) {
        healthIssues = newIssues;
    }
    public void setDoctorNote(String newNote) {
        doctorNote = newNote;
    }
    public void setVitals(Vitals newVitals) {
        vitals = newVitals;
    }
    public void setVisitNum(int visitNum){this.visitNum = visitNum;}

    public static ArrayList<VisitSummary> selectVisitSummary(int patientID){
        String sql = "SELECT visitNumber, date, doctorNote "
                + "FROM VisitSummary WHERE patientID = ?";

        ArrayList<VisitSummary> visits = new ArrayList<>();

        // Connects
        try {

            PreparedStatement pstmt  = conn.prepareStatement(sql);
            // Creates a prepared statement
            pstmt.setInt(1,patientID);
            ResultSet result  = pstmt.executeQuery();

            VisitSummary newSummary;
            // Loops through queries
            while (result.next()) {
                newSummary = new VisitSummary();
                newSummary.setVisitNum(result.getInt("visitNumber"));
                newSummary.setDate(result.getString("date"));
                newSummary.setDoctorNote(result.getString("doctorNote"));
                newSummary.setVitals(selectVitals(newSummary.getVisitNum()));
                newSummary.setHealthIssues(selectHealth(newSummary.getVisitNum()));
                visits.add(newSummary);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return visits;
    }

    public static Vitals selectVitals(int visitNum)
    {
        String sql = "SELECT heartRate, temperature, bloodPressure,respRate "
                + "FROM VITALS WHERE visitNumber = ?";

        // Connects
        try {

            PreparedStatement pstmt  = conn.prepareStatement(sql);
            // Creates a prepared statement
            pstmt.setInt(1,visitNum);
            ResultSet result  = pstmt.executeQuery();

            result.next();
            return new Vitals(result.getDouble("bloodPressure"), result.getDouble("heartRate"), result.getDouble("respRate"), result.getDouble("temperature"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static ArrayList<HealthIssues> selectHealth(int visitNum)
    {
        String sql = "SELECT description, name, date "
                + "FROM HEALTHISSUE WHERE visitNumber = ?";

        ArrayList<HealthIssues> issues = new ArrayList<>();

        // Connects
        try {

            PreparedStatement pstmt  = conn.prepareStatement(sql);
            // Creates a prepared statement
            pstmt.setInt(1,visitNum);
            ResultSet result  = pstmt.executeQuery();


            while (result.next()) {
                HealthIssues newIssue = new HealthIssues();
                newIssue.setDescription(result.getString("description"));
                newIssue.setDate(result.getString("date"));
                newIssue.setName(result.getString("name"));
                issues.add(newIssue);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return issues;
    }
}
