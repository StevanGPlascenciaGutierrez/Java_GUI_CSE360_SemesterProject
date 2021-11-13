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

    public static int loginStaff(int id, String pass){
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
            stmt.close();

            return true;
        }catch(SQLException e){
          return false;
        }
    }

    public static String getUser(int id) throws SQLException {
        String sql = "SELECT name FROM Patient WHERE patientID = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
        String tepm = rs.getString("name");
        return tepm;
    }

    public static void changeScene (Button butt,String file, int id) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        LoggedController cont = loader.getController();
        cont.setName(id);
        cont.setID(id);

        Stage window = (Stage) butt.getScene().getWindow();
        Scene scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    public void close(Connection conn){
        try {
            conn.close();
        }catch(SQLException e){
            return;
        }
    }
}
