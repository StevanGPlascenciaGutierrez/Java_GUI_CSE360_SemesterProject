package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class Appointment {
    private String time;
    private String date;
    private String name;
    private Doctor doctor;
    private Patient patient;
    private Nurse nurse;

    public Appointment(){
        this.name = null;

    }

    public Appointment(String time, String date, String name, Doctor doctor, Patient patient, Nurse nurse){
        this.time = time;
        this.date = date;
        this.name = name;
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
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
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
    public ArrayList<String> selectByDName(String Dname, String date){

        //SQL Query
        String sql = "SELECT time, date FROM APPOINTMENT WHERE doctorID = (SELECT doctorID FROM Doctor WHERE name = ?) AND date = ?";

        try {//Gets Appointment Time
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Dname);
            pstmt.setString(2, date);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<String> arr = new ArrayList<>();

            while (rs.next()) {
                arr.add(rs.getString("time"));
            }

            return arr;
        }
        catch (SQLException e) {
            return null;
        }
    }



}
