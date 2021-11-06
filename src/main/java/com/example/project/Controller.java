package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Hyperlink signUp, backLogin, doctorLogin, backPatDash;
    @FXML
    private Button loginButton, logoutButton, toMessages, appButt, visitSumButt, patHealthHistClick, prescriptionClick;

    @FXML
    protected void onPatHealthClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Patient Health History.fxml"));
        Stage window = (Stage) patHealthHistClick.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void onPreClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("PrescriptionHistory.fxml"));
        Stage window = (Stage) prescriptionClick.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void onVisitClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Visit Summary.fxml"));
        Stage window = (Stage) visitSumButt.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void onAppButtClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("PatientAppointment.fxml"));
        Stage window = (Stage) appButt.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void onMessagesClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("messages.fxml"));
        Stage window = (Stage) toMessages.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void PatDashClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Patient Dashboard.fxml"));
        Stage window = (Stage) backPatDash.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void onLogout() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("login.fxml"));
        Stage window = (Stage) logoutButton.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void onSignUpClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Patient Sign Up.fxml"));
        Stage window = (Stage) signUp.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }
    @FXML
    protected void onDoctorLoginClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Staff Login.fxml"));
        Stage window = (Stage) doctorLogin.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void onBackLogin() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("login.fxml"));
        Stage window = (Stage) backLogin.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }

    @FXML
    protected void onLogin() throws Exception {
        Parent root = FXMLLoader.load(Project.class.getResource("Patient Dashboard.fxml"));
        Stage window = (Stage) loginButton.getScene().getWindow();
        window.setScene(new Scene(root, 1920, 1080));
        window.setFullScreen(true);
    }
}