package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.example.project.Connect.conn;

public class Vitals {
    private double bloodPressure;
    private double heartRate;
    private double respiratoryRate;
    private double bodyTemp;

    public Vitals(){
    }

    public Vitals(Double bloodPressure, Double heartRate, Double respiratoryRate, Double bodyTemp){
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.respiratoryRate = respiratoryRate;
        this.bodyTemp = bodyTemp;
    }

    public Double getBloodPressure() {
        return bloodPressure;
    }

    public Double getHeartRate() {
        return heartRate;
    }

    public Double getRespiratoryRate() {
        return respiratoryRate;
    }

    public Double getBodyTemp(){
        return bodyTemp;
    }

    public void setBloodPressure(Double newBloodPressure) {
        this.bloodPressure = newBloodPressure;
    }

    public void setHeartRate(Double newHeartRate) {
        this.heartRate = newHeartRate;
    }

    public void setRespiratoryRate(Double newRespiratoryRate) {
        this.respiratoryRate = newRespiratoryRate;
    }

    public void setDescription(Double newBodyTemp) {
        this.bodyTemp = newBodyTemp;
    }

    public static void insert(double BloodPressure, double HeartRate, double RespiratoryRate, double BodyTemp, int id){
        String sql = "INSERT INTO Vitals(bloodPressure, heartRate, respiratoryRate, bodyTemp, patientID) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, BloodPressure);
            pstmt.setDouble(2, HeartRate);
            pstmt.setDouble(3, RespiratoryRate);
            pstmt.setDouble(4, BodyTemp);
            pstmt.setInt(5, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}