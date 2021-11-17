package com.example.project;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
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

        DoctorDashboard dash = new DoctorDashboard();

        //Hold needed Objects
        visitSummary = null;
        ArrayList<Appointment> appointments = new ArrayList<Appointment>();
        ArrayList<Message> messages = new ArrayList<Message>();

        //SQL Query Strings
        String aptSQL = "SELECT * FROM appointment WHERE doctorID = ? ";

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

                if (LocalDate.parse(date).compareTo(LocalDate.now()) < 0 && (LocalTime.parse(time).compareTo(LocalTime.now()) < 0)) {
                    Appointment app = new Appointment(time,date,pat.getName(),doc,pat,nur); //checks if appointment has passed
                    deleteAppointment(app);
                }
                else {
                    appointments.add(new Appointment(time,date,pat.getName(),doc,pat,nur));
                }

            }
            dash.setAppointments(appointments);
            dash.setMessages(messages);
            dash.setVisitSummary(visitSummary);

            return dash;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void deleteAppointment(Appointment app) {
        String sql = "DELETE FROM Appointment WHERE time = ? and doctorid = ? and date = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, app.getTime());
            pstmt.setInt(2, app.getDoctor().getStaffID());
            pstmt.setString(3, app.getDate());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
