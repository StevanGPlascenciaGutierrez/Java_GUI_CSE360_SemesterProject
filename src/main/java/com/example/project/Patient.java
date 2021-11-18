package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class Patient {
    private String name;
    private int ID;
    private String email;
    private String birthday;
    private String address;
    private int phoneNum;
    private int age;
    private String password;
    private Doctor currentDoctor;
    private Hyperlink link;

    public Patient() {
    }

    public Patient(String name, double weight, double height, int ID, String email, String birthday, String address, int phoneNum, int age, double BMI, String password, Doctor currentDoctor, Hyperlink link) {
        this.name = name;
        this.ID = ID;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.phoneNum = phoneNum;
        this.age = age;
        this.password = password;
        this.currentDoctor = currentDoctor;
        this.link = link;
    }

    public boolean isDependent()
    {
        if (age < 13)
        {
            return true;
        }
        return false;
    }

    public Hyperlink getLink() {
        return link;
    }

    public void setLink(Hyperlink link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public void setCurrentDoctor(Doctor currentDoctor) {
        this.currentDoctor = currentDoctor;
    }

    public static void insert(String Name, String Address, String Email, int phoneNum){
        String sql = "INSERT INTO PATIENT(name, address, emailAddress, phoneNumber) VALUES(?,?,?,?)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, Name);
            pstmt.setString(2, Address);
            pstmt.setString(3, Email);
            pstmt.setInt(4,phoneNum);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public Patient getPatientObject(int id) throws SQLException {
        String sql = "SELECT * FROM Doctor WHERE patientID = ?";

        Patient pat = new Patient();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            pat.setName(rs.getString("name"));
            pat.setPassword(rs.getString("password"));
            pat.setID(id);
            pat.setBirthday(rs.getString("birthday"));
            pat.setEmail(rs.getString("emailAddress"));
            pat.setPhoneNum(rs.getInt(Integer.parseInt(rs.getString("phoneNumber"))));
            pat.setCurrentDoctor(new Doctor().getDoctorObject(rs.getInt(Integer.parseInt("currentDoctor"))));

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return pat;
    }

    public void update(Patient pat, int id){
        String sql = "Update Patient Set name = ?, phoneNumber = ?, address = ?, emailAddress = ? WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pat.getName());
            pstmt.setInt(2, pat.getPhoneNum());
            pstmt.setString(3, pat.getAddress());
            pstmt.setString(4, pat.getEmail());
            pstmt.setInt(5, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateDoctor(int id, int doctor){
        String sql = "Update Patient Set currentDoctor = ? WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, doctor);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getPassword(int id) throws SQLException {
        String sql = "SELECT password FROM Patient WHERE patientID = ?";
        String pass = "";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            pass = rs.getString("password");

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return pass;
    }

    public boolean updatePassword(int id, String oldPassword, String newPassword) throws SQLException {
        String sql = "Update Patient Set password = ? WHERE patientID = ?";
        if (getPassword(id).equals(oldPassword)) {
            try {

                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, newPassword);
                pstmt.setInt(2, id);
                pstmt.executeUpdate();
            }
            catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return true;
        }

        return false;
    }

    public void delete(int id){
        String sql = "DELETE FROM Patient WHERE patientID = ?";

        try {
            deleteAllergies(id);
            deleteAppointments(id);
            deleteChat(id);
            deleteImmunizations(id);
            deleteInsurance(id);
            deletePharmacy(id);
            deleteVitals(id);
            deleteVS(id);
            deletePrescriptions(id);

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAppointments(int id){
        String sql = "DELETE FROM Appointment WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteImmunizations(int id){
        String sql = "DELETE FROM Immunization WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAllergies(int id){
        String sql = "DELETE FROM Allergy WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteInsurance(int id){
        String sql = "DELETE FROM Insurance WHERE patientID = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Integer> getChatNums(int id){
        String sql = "SELECT ChatNum FROM Chat WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            ArrayList<Integer> arr = new ArrayList<Integer>();

            while (rs.next()) {
                arr.add(rs.getInt("chatNum"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void deleteChat(int id){
        String sql = "DELETE FROM Messages WHERE chatNum = ?";
        String sqlTwo = "DELETE FROM Messages WHERE chatNum = ?";
        try {

            ArrayList<Integer> arr = getChatNums(id);

            try {
                for (int chatNum : arr) {
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, chatNum);
                    pstmt.executeUpdate();

                    PreparedStatement stmt = conn.prepareStatement(sqlTwo);
                    stmt.setInt(1, chatNum);
                    stmt.executeUpdate();

                }
            }

            catch (NullPointerException e) {

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePharmacy(int id){
        String sql = "DELETE FROM Pharamacy WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deletePrescriptions(int id){
        String sql = "DELETE FROM Prescription WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteVS(int id){
        String sql = "DELETE FROM VisitSummary WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteVitals(int id){
        String sql = "DELETE FROM Vitals WHERE patientID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
