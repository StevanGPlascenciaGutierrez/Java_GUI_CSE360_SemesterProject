package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggedController {

    @FXML
    Label nameLabel, patPhone, patName, patEmail, patAddress, patDoctor, insName, insPhone, insID, insAddress;

    @FXML
    private Hyperlink backPatDash, backDocDash;
    @FXML
    private Button submitNewInsurance, submitNewPatient, deletePatient, logoutButton, toMessages, appButt, visitSumButt, patHealthHistClick, prescriptionClick, docPatients, docVisit;



    private Parent root;
    private Scene scene;
    private Stage window;

    private int userID;
    private int userDoctor;

    @FXML
    public void loadFX(Button butt, String file) throws Exception {
        root = FXMLLoader.load(Project.class.getResource(file));
        window = (Stage) butt.getScene().getWindow();

        scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void loadFX(Hyperlink butt, String file) throws Exception {
        root = FXMLLoader.load(Project.class.getResource(file));
        window = (Stage) butt.getScene().getWindow();
        scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void setName(int id) throws SQLException {
        ArrayList<String> arr = Connect.getUser(id);
        nameLabel.setText(arr.get(0));
        patName.setText(arr.get(0));
        patPhone.setText(arr.get(1));
        patAddress.setText(arr.get(2));
        patEmail.setText(arr.get(3));
        patDoctor.setText(arr.get(4));
    }

    @FXML
    protected void setDash(PatientDashboard pat) throws Exception {
        ArrayList<Immunization> imm;
        Pharmacy pharm;
        Insurance ins;

        ins = pat.getInsurance();
        try {
            imm = pat.getImmunizations();
            pharm = pat.getPharmacy();


        }
        catch (NullPointerException e) {

        }

        insName.setText(ins.getName());
        insPhone.setText(Integer.toString(ins.getPhoneNumber()));
        insAddress.setText(ins.getName());
        insID.setText(ins.getName());

    }


    protected void setID(int id) {
        this.userID = id;
    }

    protected int getID() {
        return userID;
    }

    protected void setDoctorID(int id) {
        this.userDoctor = id;
    }

    protected int getDoctorID() {
        return userDoctor;
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
        Connect.changeScene(visitSumButt, "Visit Summary.fxml", this.getID());
        VisitSummary.selectVisitSummary(this.getID());
    }

    @FXML
    protected void onAppButtClick() throws Exception{
        loadFX(appButt, "PatientAppointment.fxml");
    }

    @FXML
    protected void onMessagesClick() throws Exception{
        Connect.changeScene(toMessages, "messages.fxml", this.getID());
        Message.getChat(this.getID(), this.getDoctorID());
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


}
