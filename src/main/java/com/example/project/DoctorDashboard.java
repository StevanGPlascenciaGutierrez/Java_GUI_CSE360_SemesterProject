package com.example.project;

import java.sql.*;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class DoctorDashboard {
    private VisitSummary visitSummary;
    private ArrayList<Appointment> appointments;
    private ArrayList<Message> messages;

    public DoctorDashboard() {
        visitSummary = null;
        appointments = new ArrayList<Appointment>();
        messages = new ArrayList<Message>();
    }
    public DoctorDashboard(VisitSummary summary, ArrayList<Appointment> appointments, ArrayList<Message> messages) {
        visitSummary = summary;
        this.appointments = appointments;
        this.messages = messages;
    }
    public VisitSummary getVisitSummary() {
        return visitSummary;
    }
    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }
    public ArrayList<Message> getMessages() {
        return messages;
    }
    public void setVisitSummary(VisitSummary newSummary) {
        visitSummary = newSummary;
    }
    public void setAppointments(ArrayList<Appointment> newAppts) {
        appointments = newAppts;
    }
    public void setMessages(ArrayList<Message> newMessages) {
        messages = newMessages;
    }

    //Returns Doctor Dashboard object from database;
    public DoctorDashboard select(int doctorID) {

        //Hold needed Objects
        visitSummary = null;
        appointments = new ArrayList<Appointment>();
        messages = new ArrayList<Message>();

        //SQL Query Strings
        String aptSQL = "SELECT * FROM appointments WHERE doctorID = ? ";

        //Connects To Database
        try  {

            //Prepares Statement
            PreparedStatement pstmt = conn.prepareStatement(aptSQL);
            pstmt.setInt(1,doctorID);

            ResultSet rs = pstmt.executeQuery();

            //Gets Appointments from database
            while(rs.next()){

                //Gets Time
                String time = rs.getString("time");

                //Gets Date
                String date = rs.getString("date");

                //Gets Patient ID
                int patientID = rs.getInt("patientID");

                //Gets Nurse ID
                int nurseID = rs.getInt("nurseID");

                //Gets Doctor
                String docSQL = "SELECT * from Doctor WHERE doctorID = ?";
                pstmt = conn.prepareStatement(docSQL);
                pstmt.setInt(1,doctorID);
                ResultSet ts = pstmt.executeQuery();
                Doctor doc = new Doctor(ts.getString("Name"),doctorID,ts.getString("password"));

                //Gets Patient Name and ID
                String patSQL = "SELECT * from Patient WHERE patientID = ?";
                pstmt = conn.prepareStatement(patSQL);
                pstmt.setInt(1,patientID);
                ts = pstmt.executeQuery();
                Patient pat = new Patient();
                pat.setName(ts.getString("name"));
                pat.setID(patientID);

                //Gets Nurse
                String nurSQL = "SELECT * from Nurse WHERE nurseID = ?";
                pstmt = conn.prepareStatement(nurSQL);
                pstmt.setInt(1,nurseID);
                ts = pstmt.executeQuery();
                Nurse nur = new Nurse(ts.getString("name"),nurseID, ts.getString("password"), doc);


                appointments.add(new Appointment(time,date,doc,pat,nur));

            }

            return this;
        } catch (SQLException e) {
            return null;
        }
    }

    //Gets Doctor Dashboard from Nurse ID
    public DoctorDashboard nurseSelect(int nurseID){

        //SQL Query
        String nurSQL = "SELECT doctorID FROM Nurse WHERE nurseID = ?";

        try{//Returns Dashboard by getting the Nurse's Doctor
            PreparedStatement pstmt = conn.prepareStatement(nurSQL);
            pstmt.setInt(1,nurseID);
            ResultSet rs = pstmt.executeQuery();
            int doctorID = rs.getInt("doctorID");

            return this.select(doctorID);
        }catch(SQLException e){
            return null;
        }
    }



}
