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

import java.io.IOException;
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
    Label pharmName, pharmAddress, pharmPhone;

    @FXML
    private Hyperlink backPatDash;
    @FXML
    private Button submitNewInsurance, submitNewPatient, logoutButton, toMessages, visitSumButt, patHealthHistClick, prescriptionClick;



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


    public void changeScene (Button butt,String file, int id) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        LoggedController cont = loader.getController();

        cont.setID(id);


        Stage window = (Stage) butt.getScene().getWindow();
        Scene scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
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
        imm = pat.getImmunizations();
        pharm = pat.getPharmacy();

        insName.setText(ins.getName());
        insPhone.setText(Integer.toString(ins.getPhoneNumber()));
        insAddress.setText(ins.getName());
        insID.setText(ins.getName());

        pharmName.setText(pharm.getName());
        pharmAddress.setText(pharm.getAddress());
        pharmPhone.setText(Integer.toString(pharm.getPhoneNumber()));

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
    protected void onPatHealthClick() throws Exception{
        changeScene(patHealthHistClick, "Patient Health History.fxml", this.getID());
    }

    @FXML
    protected void onPreClick() throws Exception{
        changeScene(prescriptionClick, "PrescriptionHistory.fxml", this.getID());
    }

    @FXML
    protected void onVisitClick() throws Exception{
        changeScene(visitSumButt, "Visit Summary.fxml", this.getID());
        VisitSummary.selectVisitSummary(this.getID());
    }

    @FXML
    protected void onAppButtClick() throws Exception{
        changeScene(toMessages, "PatientAppointment.fxml", this.getID());
    }

    @FXML
    protected void onMessagesClick() throws Exception{
        changeScene(toMessages, "messages.fxml", this.getID());
        Message.getChat(this.getID(), this.getDoctorID());
    }

    protected void logIn (Hyperlink butt,String file, int id, PatientDashboard pat) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        LoggedController cont = loader.getController();

        cont.setID(id);
        cont.setDoctorID(Connect.getPatientDoctor(id));
        cont.setName(id);
        cont.setDash(pat);


        Stage window = (Stage) butt.getScene().getWindow();
        Scene scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void PatDashClick() throws Exception{
        PatientDashboard pat = new PatientDashboard();
        pat.select(this.getID());
        logIn(backPatDash, "Patient Dashboard.fxml", this.getID(), pat);
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
