package com.example.project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class Patient {
    private String name;
    private double weight;
    private double height;
    private int ID;
    private String email;
    private String birthday;
    private String address;
    private int phoneNum;
    private int age;
    private double BMI;
    private String password;
    private Doctor currentDoctor;

    public Patient() {
    }

    public Patient(String name, double weight, double height, int ID, String email, String birthday, String address, int phoneNum, int age, double BMI, String password, Doctor currentDoctor) {
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.ID = ID;
        this.email = email;
        this.birthday = birthday;
        this.address = address;
        this.phoneNum = phoneNum;
        this.age = age;
        this.BMI = BMI;
        this.password = password;
        this.currentDoctor = currentDoctor;
    }

    public boolean isDependent()
    {
        if (age < 13)
        {
            return true;
        }
        return false;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
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

    public double getBMI() {
        return BMI;
    }

    public void setBMI(double BMI) {
        this.BMI = BMI;
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

    public ArrayList<Patient> select(int id) {
        String sql = "SELECT * FROM Patient WHERE doctorID = ?)";
        ArrayList<Patient> patList = new ArrayList<Patient>();

        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                Patient pat = new Patient();
                pat.setID(rs.getInt("patientID"));
                pat.setName(rs.getString("name"));
                patList.add(pat);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
         return patList;
    }

}
