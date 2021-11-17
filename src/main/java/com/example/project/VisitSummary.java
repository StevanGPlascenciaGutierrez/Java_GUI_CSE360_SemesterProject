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
    private double weight;
    private double height;
    private double BMI;

    public VisitSummary() {
        date = "";
        vitals = null;
        doctorNote = "";
        healthIssues = new ArrayList<>();
        weight = 0;
        height = 0;
        BMI = 0;

    }
    public VisitSummary(String date, Vitals vitals, String doctorNote, ArrayList<HealthIssues> healthIssues, double weight, double height, double BMI) {
        this.date = date;
        this.vitals = vitals;
        this.doctorNote = doctorNote;
        this.healthIssues = healthIssues;
        this.weight = weight;
        this.height = height;
        this.BMI = BMI;
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
    public double getWeight() {
        return weight;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }
    public double getHeight() {
        return height;
    }
    public void setHeight(double height) {
        this.height = height;
    }
    public double getBMI() {
        return BMI;
    }
    public void setBMI(double BMI) {
        this.BMI = BMI;
    }

    public ArrayList<VisitSummary> selectVisitSummary(int patientID){
        String sql = "SELECT visitNumber, date, doctorNote, weight, height, bmi "
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
                newSummary.setWeight(result.getDouble("weight"));
                newSummary.setHeight(result.getDouble("height"));
                newSummary.setBMI(result.getDouble("bmi"));
                visits.add(newSummary);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return visits;
    }

    public Vitals selectVitals(int visitNum)
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

    public ArrayList<HealthIssues> selectHealth(int visitNum)
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

    public void insert(VisitSummary visit, int patientID){
        String sql = "INSERT INTO VisitSummary(patientID, date, doctorNote, weight, height, bmi) VALUES(?,?,?,?,?,?)";
        // Connects
        try{

            PreparedStatement pstmt  = conn.prepareStatement(sql);
            // Creates a prepared statement
            pstmt.setInt(1, patientID);
            pstmt.setString(2, visit.getDate());
            pstmt.setString(3, visit.getDoctorNote());
            pstmt.setDouble(4, visit.getWeight());
            pstmt.setDouble(5, visit.getHeight());
            pstmt.setDouble(6, visit.getBMI());
            pstmt.executeUpdate();

            Vitals vital = visit.getVitals();

            int num = getVisitNum(patientID, visit.getDoctorNote());

            vital.insert(vital.getBloodPressure(), vital.getHeartRate(), vital.getRespiratoryRate(), vital.getBodyTemp(), patientID, num);
            ArrayList<HealthIssues> issueList = visit.getHealthIssues();
            for (HealthIssues issue : issueList) {
                issue.insert(issue.getName(), issue.getDescription(), issue.getDate(), patientID, num);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getVisitNum(int id, String text) {
        String sql = "Select VisitNumber from VisitSummary Where patientID = ? AND doctorNote = ?";
        int num = 0;
        // Connects
        try {

            PreparedStatement pstmt = conn.prepareStatement(sql);
            // Creates a prepared statement
            pstmt.setInt(1, id);
            pstmt.setString(2, text);

            ResultSet rs = pstmt.executeQuery();
            num = rs.getInt("visitNumber");

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return num;

    }

    public VisitSummary selectVisitSummary(int patientID, String date){
        String sql = "SELECT visitNumber, date, doctorNote, weight, height, bmi "
                + "FROM VisitSummary WHERE patientID = ? AND date = ?";

        VisitSummary visit = new VisitSummary();

        // Connects
        try {

            PreparedStatement pstmt  = conn.prepareStatement(sql);
            // Creates a prepared statement
            pstmt.setInt(1,patientID);
            pstmt.setString(2, date);
            ResultSet result  = pstmt.executeQuery();

            visit = new VisitSummary();
            visit.setVisitNum(result.getInt("visitNumber"));
            visit.setDate(result.getString("date"));
            visit.setDoctorNote(result.getString("doctorNote"));
            visit.setVitals(selectVitals(visit.getVisitNum()));
            visit.setHealthIssues(selectHealth(visit.getVisitNum()));
            visit.setWeight(result.getDouble("weight"));
            visit.setHeight(result.getDouble("height"));
            visit.setBMI(result.getDouble("bmi"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return visit;
    }


}
