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
    private Hyperlink signUp, backLogin, doctorLogin, backPatDash, backDocDash;
    @FXML
    private Button loginButton, logoutButton, toMessages, appButt, visitSumButt, patHealthHistClick, prescriptionClick, staffLogin, docPatients, docVisit;

    private Parent root;
    private Scene scene;
    private Stage window;


    @FXML
    protected void onDocDashClick() throws Exception {
        root = FXMLLoader.load(Project.class.getResource("DoctorDashboard.fxml"));
        window = (Stage) backDocDash.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onDocVisitClick() throws Exception {
        root = FXMLLoader.load(Project.class.getResource("DoctorVisitSummary.fxml"));
        window = (Stage) docVisit.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onDocPatientClick() throws Exception {
        root = FXMLLoader.load(Project.class.getResource("PatientSearch.fxml"));
        window = (Stage) docPatients.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onStaffLogin() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("DoctorDashboard.fxml"));
        window = (Stage) staffLogin.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }



    @FXML
    protected void onPatHealthClick() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("Patient Health History.fxml"));
        window = (Stage) patHealthHistClick.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onPreClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("PrescriptionHistory.fxml"));
        Stage window = (Stage) prescriptionClick.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onVisitClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Visit Summary.fxml"));
        Stage window = (Stage) visitSumButt.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onAppButtClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("PatientAppointment.fxml"));
        Stage window = (Stage) appButt.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onMessagesClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("messages.fxml"));
        Stage window = (Stage) toMessages.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void PatDashClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Patient Dashboard.fxml"));
        Stage window = (Stage) backPatDash.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onLogout() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("login.fxml"));
        Stage window = (Stage) logoutButton.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onSignUpClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Patient Sign Up.fxml"));
        Stage window = (Stage) signUp.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }
    @FXML
    protected void onDoctorLoginClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Staff Login.fxml"));
        Stage window = (Stage) doctorLogin.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onBackLogin() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("login.fxml"));
        Stage window = (Stage) backLogin.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onLogin() throws Exception {
        Parent root = FXMLLoader.load(Project.class.getResource("Patient Dashboard.fxml"));
        Stage window = (Stage) loginButton.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }
}