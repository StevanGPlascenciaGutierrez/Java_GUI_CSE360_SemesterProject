package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PatientDashboard extends Dashboard {
    private Insurance insurance;
    private Pharmacy pharmacy;
    private ArrayList<Immunization> immunizations;

    public PatientDashboard() {
        insurance = null;
        pharmacy = null;
        immunizations = new ArrayList<Immunization>();
    }
    public PatientDashboard(Insurance insurance, Pharmacy pharmacy, ArrayList<Immunization> immunizations) {
        this.insurance = insurance;
        this.pharmacy = pharmacy;
        this.immunizations = immunizations;
    }
    public Insurance getInsurance() {
        return insurance;
    }
    public Pharmacy getPharmacy() {
        return pharmacy;
    }
    public ArrayList<Immunization> getImmunizations() {
        return immunizations;
    }
    public void setInsurance(Insurance newInsurance) {
        insurance = newInsurance;
    }
    public void setPharmacy(Pharmacy newPharm) {
        pharmacy = newPharm;
    }
    public void setImmunizations(ArrayList<Immunization> newImmunizations) {
        immunizations = newImmunizations;
    }

    //Returns Patient Dashboard object from SQLite Database
    public PatientDashboard select(int patientID){

        //SQLite Queries
        String insSQL = "SELECT * FROM Insurance WHERE patientID = ? ";
        String phaSQL = "SELECT * FROM Pharmacy WHERE patientID = ?";
        String immSQL = "SELECT * FROM Immunizations WHERE patientID = ?";

        //Array List to hold immunizations
        immunizations = new ArrayList<Immunization>();

        //Establish Connection
        try (Connection conn = Connect.conn ) {

            //Gets Insurance from database
            PreparedStatement pstmt = conn.prepareStatement(insSQL);
            pstmt.setInt(1, patientID);
            ResultSet rs = pstmt.executeQuery();
            insurance = new Insurance(rs.getString("name"),rs.getInt("phoneNumber"));


            //Gets Pharmacy from database
            pstmt = conn.prepareStatement(phaSQL);
            pstmt.setInt(1, patientID);
            rs = pstmt.executeQuery();
            pharmacy = new Pharmacy(rs.getString("name"),rs.getString("address"),rs.getInt("phoneNumber"));


            //Gets Immunizations from database
            pstmt = conn.prepareStatement(immSQL);
            pstmt.setInt(1,patientID);
            rs = pstmt.executeQuery();
            while(rs.next()){
                Immunization imm = new Immunization(rs.getString("type"), rs.getString("date"), rs.getString("description"));
                immunizations.add(imm);
            }

            return this;
        }catch(SQLException e){
            return null;
        }
    }
}
