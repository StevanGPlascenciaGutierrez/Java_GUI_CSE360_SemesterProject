package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggedController implements Initializable{
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = Connect.Connect();
    }

    @FXML
    private Hyperlink signUp, backLogin, doctorLogin, backPatDash, backDocDash;
    @FXML
    private Button submitNewInsurance, submitNewPatient, deletePatient, patientSignUp, loginButton, logoutButton, toMessages, appButt, visitSumButt, patHealthHistClick, prescriptionClick, staffLogin, docPatients, docVisit;

    @FXML
    private TextField patientEmail, staffID, signUpFirst, signUpLast, signUpEmail, signUpPhone, signUpAddress;

    @FXML
    private PasswordField patientPassword, staffPassword, signUpPassword;

    @FXML
    private Label signInLabel, staffSignInLabel, signUpLabel;

    @FXML
    private DatePicker signUpBday;

    private Parent root;
    private Scene scene;
    private Stage window;

    private Connection conn = null;
    private PreparedStatement pst =  null;



}
