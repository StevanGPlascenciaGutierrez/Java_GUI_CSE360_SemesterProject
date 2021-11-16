package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.example.project.Connect.conn;

public class Appointment {
    private String time;
    private String date;
    private Doctor doctor;
    private Patient patient;
    private Nurse nurse;

    public Appointment(){
    }

    public Appointment(String time, String date, Doctor doctor, Patient patient, Nurse nurse){
        this.time = time;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.nurse = nurse;
    }
    public String getTime() {
        return time;
    }

    public String getDate(){
        return date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void setTime(String newTime) {
        this.time = newTime;
    }

    public void setDate(String newDate) {
        this.date = newDate;
    }

    public void setDoctor(Doctor newDoctor) {
        this.doctor = newDoctor;
    }

    public void setPatient(Patient newPatient) {
        this.patient = newPatient;
    }

    public void setNurse(Nurse newNurse) {
        this.nurse = newNurse;
    }

    public void insert(String time, String date, int doctorID, int patientID, int nurseID){
        String sql = "INSERT INTO APPOINTMENT(time, date, doctorID, patientID, nurseID) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, time);
            pstmt.setString(2, date);
            pstmt.setInt(3, doctorID);
            pstmt.setInt(4, patientID);
            pstmt.setInt(5, nurseID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Appointment select(int id){
        String sql = "SELECT * FROM APPOINTMENT WHERE doctorID = ?";
        Appointment app = new Appointment();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            app.setTime(rs.getString("time"));
            app.setDate(rs.getString("date"));
            app.setDoctor(new Doctor().getDoctorObject(rs.getInt("doctorID")));
            app.setPatient(new Patient().getPatientObject(rs.getInt("patientID")));
            app.setNurse(new Nurse().getNurseObject(rs.getInt("nurseID")));

            return app;

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Gets Appointment time from Doctor name
    public String selectByDName(String Dname){

        //SQL Query
        String sql = "SELECT time FROM APPOINTMENT WHERE doctorID = (SELECT doctorID FROM Doctor WHERE name = ?)";

        try {//Gets Appointment Time
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Dname);
            ResultSet rs = pstmt.executeQuery();
            return rs.getString("time");
        }
        catch (SQLException e) {
            return null;
        }
    }
}
