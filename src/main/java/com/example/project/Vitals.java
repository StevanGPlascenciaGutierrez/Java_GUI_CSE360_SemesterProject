package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
    
    public static void insert(double BloodPressure, double HeartRate, double RespiratoryRate, double BodyTemp){
        String sql = "INSERT INTO PATIENT(bloodPressure, heartRate, respiratoryRate, bodyTemp) VALUES(?,?,?,?)";
        try (Connection conn = Connect.conn;
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setdouble(1, BloodPressure);
            pstmt.setdouble(2, HeartRate);
            pstmt.setdouble(3, RespiratoryRate);
            pstmt.setdouble(4, BodyTemp);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
