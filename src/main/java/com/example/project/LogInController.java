package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogInController {


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


    @FXML
    public void loadFX(Button butt, String file) throws Exception {
        root = FXMLLoader.load(Project.class.getResource(file));
        window = (Stage) butt.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void loadFX(Hyperlink butt, String file) throws Exception {
        root = FXMLLoader.load(Project.class.getResource(file));
        window = (Stage) butt.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void deletePatientData() throws Exception {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Warning");
        deleteAlert.setHeaderText("You are about to delete this patient from the system");
        deleteAlert.setContentText("Are you sure you want to delete this patient?");

        if (deleteAlert.showAndWait().get() == ButtonType.OK) {

            loadFX(deletePatient, "DoctorDashboard.fxml");
        }
    }

    @FXML
    protected void onDocDashClick() throws Exception {
        loadFX(backDocDash, "DoctorDashboard.fxml");
    }

    @FXML
    protected void onDocVisitClick() throws Exception {
        loadFX(docVisit, "DoctorVisitSummary.fxml");
    }

    @FXML
    protected void onDocPatientClick() throws Exception {
        loadFX(docPatients, "PatientSearch.fxml");
    }

    @FXML
    protected void onPatHealthClick() throws Exception{
        loadFX(patHealthHistClick, "Patient Health History.fxml");
    }

    @FXML
    protected void onPreClick() throws Exception{
        loadFX(prescriptionClick, "PrescriptionHistory.fxml");
    }

    @FXML
    protected void onVisitClick() throws Exception{
        loadFX(visitSumButt, "Visit Summary.fxml");
    }

    @FXML
    protected void onAppButtClick() throws Exception{
        loadFX(appButt, "PatientAppointment.fxml");
    }

    @FXML
    protected void onMessagesClick() throws Exception{
        loadFX(toMessages, "messages.fxml");
    }

    @FXML
    protected void PatDashClick() throws Exception{
        loadFX(backPatDash, "Patient Dashboard.fxml");
    }

    @FXML
    protected void onLogout() throws Exception{
        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
        logoutAlert.setTitle("Logout");
        logoutAlert.setHeaderText("You're about to log out");
        logoutAlert.setContentText("Are you sure you want to log out");

        if (logoutAlert.showAndWait().get() == ButtonType.OK) {
            loadFX(logoutButton, "login.fxml");
        }
    }

    @FXML
    protected void onSubmitPatient() throws Exception {
        Stage box = (Stage) submitNewPatient.getScene().getWindow();
        box.close();
    }

    @FXML
    protected void onEditPatient() throws Exception {
        Parent root = FXMLLoader.load(Project.class.getResource("EditPatient.fxml"));
        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();

    }

    @FXML
    protected void onSubmitInsurance() throws Exception {
        Stage box = (Stage) submitNewInsurance.getScene().getWindow();
        box.close();
    }

    @FXML
    protected void onEditInsurance() throws Exception {
        Parent root = FXMLLoader.load(Project.class.getResource("EditInsurance.fxml"));
        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();

    }

    @FXML
    protected void onStaffLogin() throws Exception{
        if (staffID.getText().isBlank() || staffPassword.getText().isBlank()) {
            staffSignInLabel.setText("Please enter an email and password");
        }
        else {
            loadFX(staffLogin, "DoctorDashboard.fxml");
        }
    }

    @FXML
    protected void onSignUpClick() throws Exception{
        loadFX(signUp, "Patient Sign Up.fxml");
    }
    @FXML
    protected void onDoctorLoginClick() throws Exception{
        loadFX(doctorLogin, "Staff Login.fxml");
    }

    @FXML
    protected void onBackLogin() throws Exception{
        loadFX(backLogin, "login.fxml");
    }

    @FXML
    protected void onPatientSignUp() throws Exception {
        try {
            if (signUpFirst.getText().isBlank() || signUpLast.getText().isBlank() ||
                    signUpEmail.getText().isBlank() || signUpPassword.getText().isBlank() ||
                    signUpBday.getValue().toString().isBlank() || signUpAddress.getText().isBlank() || signUpPhone.getText().isBlank()) {
                signUpLabel.setText("Please enter valid entries for each field");
            }
            else {

                String email = signUpEmail.getText();
                String password = signUpPassword.getText();
                String bday = signUpBday.getValue().toString();
                String address = signUpAddress.getText();
                long phone = Long.parseLong(signUpPhone.getText());

                Connect.signUp(signUpFirst.getText(), signUpLast.getText(), email, password, bday, address, phone);

                loadFX(patientSignUp, "login.fxml");

            }
        }
        catch (NullPointerException e){
            signUpLabel.setText("Please enter a valid birthday");
        }

    }

    @FXML
    protected void onLogin() throws Exception {
        if (patientEmail.getText().isBlank() || patientPassword.getText().isBlank()) {
            signInLabel.setText("Please enter an email and password");
        }
        else {
            System.out.println(Connect.loginPatient(patientEmail.getText(), patientPassword.getText()));
            loadFX(loginButton, "Patient Dashboard.fxml");
        }

    }
}