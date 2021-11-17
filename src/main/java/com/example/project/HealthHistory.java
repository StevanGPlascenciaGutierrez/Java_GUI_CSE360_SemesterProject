package com.example.project;

import java.lang.reflect.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class HealthHistory {
    private ArrayList<HealthIssues> issues;
    private ArrayList<Allergy> allergies;

    public HealthHistory () {
        issues = new ArrayList<>();
        allergies = new ArrayList<>();
    }

    public HealthHistory(ArrayList<HealthIssues> issues, ArrayList<Allergy> allergy) {
        this.issues = issues;
        this.allergies = allergy;
    }

    public ArrayList<HealthIssues> getIssues() {
        return this.issues;
    }

    public void setIssues(ArrayList<HealthIssues> issues) {
        this.issues = issues;
    }

    public ArrayList<Allergy> getAllergies() {
        return this.allergies;
    }

    public void setAllergies(ArrayList<Allergy> allergies) {
        this.allergies = allergies;
    }

    public HealthHistory select(int patientID) {

        HealthHistory history = new HealthHistory();

        history.setAllergies(history.selectAllergy(patientID));
        history.setIssues(history.selectIssue(patientID));

        return history;
    }

    public ArrayList<Allergy> selectAllergy(int id){
        String sql = "Select * FROM ALLERGY WHERE patientID = ?";
        ArrayList<Allergy> allergyList = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Allergy allergy = new Allergy();
                allergy.setType(rs.getString("type"));
                allergy.setDescription(rs.getString("description"));
                allergyList.add(allergy);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allergyList;
    }

    public ArrayList<HealthIssues> selectIssue(int id){
        String sql = "Select * FROM HealthIssue WHERE patientID = ?";
        ArrayList<HealthIssues> issueList = new ArrayList<>();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                HealthIssues issue = new HealthIssues();
                issue.setDate(rs.getString("date"));
                issue.setName(rs.getString("name"));
                issue.setDescription(rs.getString("description"));
                issueList.add(issue);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return issueList;
    }

}
