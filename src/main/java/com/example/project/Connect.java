package com.example.project;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Locale;


public class Connect {
    static final String url = "jdbc:sqlite:db/CSE360PROJECT.db";
    Connection conn = null;

    public static Connection Connect(){//Sets connection to database
        try{
            Connection conn = DriverManager.getConnection(url);
            System.out.println("Connection Established");
            return conn;
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int loginPatient(String user, String pass) {
        String sql = "SELECT email, password, patientID FROM Patient WHERE email = ?";

        try (Connection conn = Connect()) {
            user = user.toLowerCase();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,user);
            ResultSet rs = stmt.executeQuery(sql);
            String email = rs.getString("email");
            String password = rs.getString("password");
            int patientID = rs.getInt("patientID");
            if(user == email){
                if(pass == password){
                    return patientID;
                }
            }
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public int loginStaff(int id, String pass){
        String sql = "SELECT doctorID, password FROM Patient WHERE doctorID = ?";

        try (Connection conn = Connect()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1,id);
            ResultSet rs = stmt.executeQuery(sql);
            int doctorID = rs.getInt("doctorID");
            String password = rs.getString("password");
            if(id == doctorID){
                if(pass == password){
                    return doctorID;
                }
            }
            return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    public boolean signUp(String fName, String lName, String bDay, String email, String pass){
        try(Connection conn = Connect()){
            String sql = "INSERT INTO Patient(name, birthday, emailAddress, password) VALUES (?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,fName+lName);
            stmt.setString(2, bDay);
            stmt.setString(3,email);
            stmt.setString(4,pass);
            return true;
        }catch(SQLException e){
          return false;
        }
    }

    public void close(Connection conn){
        try {
            conn.close();
        }catch(SQLException e){
            return;
        }
    }
}
