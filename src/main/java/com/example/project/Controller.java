package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Hyperlink signUp, backLogin, doctorLogin, backPatDash, backDocDash;
    @FXML
    private Button submitNewInsurance, submitNewPatient, deletePatient, patientSignUp, loginButton, logoutButton, toMessages, appButt, visitSumButt, patHealthHistClick, prescriptionClick, staffLogin, docPatients, docVisit;

    @FXML
    private TextField patientEmail, staffID, signUpFirst, signUpLast, signUpEmail;

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
    protected void deletePatientData() throws Exception {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Warning");
        deleteAlert.setHeaderText("You are about to delete this patient from the system");
        deleteAlert.setContentText("Are you sure you want to delete this patient?");

        if (deleteAlert.showAndWait().get() == ButtonType.OK) {

            root = FXMLLoader.load(Project.class.getResource("DoctorDashboard.fxml"));
            window = (Stage) deletePatient.getScene().getWindow();
            scene = new Scene(root);
            window.setScene(scene);
            window.show();
        }
    }

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
        if (staffID.getText().isBlank() || staffPassword.getText().isBlank()) {
            staffSignInLabel.setText("Please enter an email and password");
        }
        else {
            root = FXMLLoader.load(Project.class.getResource("DoctorDashboard.fxml"));
            window = (Stage) staffLogin.getScene().getWindow();
            scene = new Scene(root);
            window.setScene(scene);
            window.show();
        }
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
        root = FXMLLoader.load(Project.class.getResource("PrescriptionHistory.fxml"));
        window = (Stage) prescriptionClick.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onVisitClick() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("Visit Summary.fxml"));
        window = (Stage) visitSumButt.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onAppButtClick() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("PatientAppointment.fxml"));
        window = (Stage) appButt.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onMessagesClick() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("messages.fxml"));
        window = (Stage) toMessages.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void PatDashClick() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("Patient Dashboard.fxml"));
        window = (Stage) backPatDash.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onLogout() throws Exception{
        Alert logoutAlert = new Alert(Alert.AlertType.CONFIRMATION);
        logoutAlert.setTitle("Logout");
        logoutAlert.setHeaderText("You're about to log out");
        logoutAlert.setContentText("Are you sure you want to log out");

        if (logoutAlert.showAndWait().get() == ButtonType.OK) {
            root = FXMLLoader.load(Project.class.getResource("login.fxml"));
            window = (Stage) logoutButton.getScene().getWindow();
            scene = new Scene(root);
            window.setScene(scene);
            window.show();
        }
    }

    @FXML
    protected void onSignUpClick() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("Patient Sign Up.fxml"));
        window = (Stage) signUp.getScene().getWindow();
        scene = new Scene(root);
        window.setScene(scene);
        window.show();

    }
    @FXML
    protected void onDoctorLoginClick() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("Staff Login.fxml"));
        window = (Stage) doctorLogin.getScene().getWindow();
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
    protected void onPatientSignUp() throws Exception {
        try {
            if (signUpFirst.getText().isBlank() || signUpLast.getText().isBlank() || signUpEmail.getText().isBlank() || signUpPassword.getText().isBlank() || signUpBday.getValue().toString().isBlank()) {
                signUpLabel.setText("Please enter valid entries for each field");
            }
            else {
                root = FXMLLoader.load(Project.class.getResource("login.fxml"));
                window = (Stage) patientSignUp.getScene().getWindow();
                scene = new Scene(root);
                window.setScene(scene);
                window.show();

            }
        }
        catch (NullPointerException e){
            signUpLabel.setText("Please enter a valid birthday");
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
    protected void onLogin() throws Exception {
        if (patientEmail.getText().isBlank() || patientPassword.getText().isBlank()) {
            signInLabel.setText("Please enter an email and password");
        }
        else {
            root = FXMLLoader.load(Project.class.getResource("Patient Dashboard.fxml"));
            window = (Stage) loginButton.getScene().getWindow();
            scene = new Scene(root);
            window.setScene(scene);
            window.show();
        }

    }
}