package com.example.project;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Locale;


public class Connect {
    public static final String url = "jdbc:sqlite:db/CSE360PROJECT.db";
    static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static Connection Connect(){//Sets connection to database
        try{
            conn = DriverManager.getConnection(url);
            System.out.println("Connection Established");
            return conn;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static int loginPatient(String user, String pass) {
        String sql = "SELECT emailAddress, password, patientID FROM Patient WHERE emailAddress = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,user);
            ResultSet rs = stmt.executeQuery();
            String email = rs.getString("emailAddress");
            String password = rs.getString("password");
            int patientID = rs.getInt("patientID");
            if(user.equals(email) && pass.equals(password)){
                return patientID;
            }
            return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int loginStaff(int id, String pass){
        String sql = "SELECT doctorID, password FROM Doctor WHERE doctorID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            int doctorID = rs.getInt("doctorID");
            String password = rs.getString("password");
            if(id == doctorID){
                if(pass.equals(password)){
                    return doctorID;
                }
            }
            return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public int loginNurse(int id, String pass){
        String sql = "SELECT nurseID, password FROM Nurse WHERE nurseID = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery();
            int nurseID = rs.getInt("nurseID");
            String password = rs.getString("password");
            if(id == nurseID){
                if(pass.equals(password)){
                    return nurseID;
                }
            }
            return -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
    }

    public static boolean signUp(String fName, String lName, String bDay, String email, String pass, String address, Long phone){
        try {
            String sql = "INSERT INTO Patient(name, emailAddress, birthday, address, phoneNumber, password) VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,fName+" " +lName);
            stmt.setString(2,email);
            stmt.setString(3, bDay);
            stmt.setString(4,address);
            stmt.setLong(5,phone);
            stmt.setString(6,pass);
            stmt.executeUpdate();

            return true;
        }catch(SQLException e){
          return false;
        }
    }

    public static String getDoctorName(int id) throws SQLException {
        String docSQL = "SELECT name FROM Doctor WHERE doctorID = (SELECT currentDoctor FROM Patient WHERE patientID = ?)";
        String temp;
        PreparedStatement stmt = conn.prepareStatement(docSQL);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        temp = rs.getString("name");

        rs.close();

        return temp;
    }

    public static ArrayList<String> getUser(int id) throws SQLException {
        String sql = "SELECT name, phoneNumber, emailAddress, address FROM Patient WHERE patientID = ?";

        ArrayList<String> arr = new ArrayList<String>();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            arr.add(rs.getString("name"));
            arr.add(rs.getString("phoneNumber"));
            arr.add(rs.getString("address"));
            arr.add(rs.getString("emailAddress"));
            rs.close();
            arr.add(getDoctorName(id));
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            arr = null;
        }

        return arr;
    }

    public static int getPatientDoctor(int id) {
        String sql = "SELECT currentDoctor FROM Patient WHERE patientID = ?";
        int doctorID;
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            doctorID = rs.getInt("currentDoctor");
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            doctorID = 0;
        }

        return doctorID;
    }

    public void close(Connection conn){
        try {
            conn.close();
        }catch(SQLException e){
            return;
        }
    }
}
