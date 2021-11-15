package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class LoggedStaffController {

    @FXML
    private Hyperlink backDocDash;

    @FXML
    private Button deletePatient, appButt, docPatients, docVisit, logoutButton;

    @FXML
    private Label nameLabel;

    private Parent root;
    private Scene scene;
    private Stage window;

    private int userID;

    protected void setID(int id) {
        this.userID = id;
    }

    protected int getID() {
        return userID;
    }

    protected void changeScene (Button butt,String file, int id) throws IOException, SQLException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        root = loader.load();

        LoggedStaffController cont = loader.getController();

        cont.setID(id);

        window = (Stage) butt.getScene().getWindow();
        scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    protected void logIn (Hyperlink butt,String file, int id, DoctorDashboard doc) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        root = loader.load();

        LoggedStaffController cont = loader.getController();

        cont.setID(id);
        cont.setName(id);

        window = (Stage) butt.getScene().getWindow();
        scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void setName(int id) throws SQLException {
        String docSQL = "SELECT name FROM Doctor WHERE doctorID = (SELECT currentDoctor FROM Patient WHERE patientID = ?)";
        String temp;
        PreparedStatement stmt = conn.prepareStatement(docSQL);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        temp = rs.getString("name");

        rs.close();

        nameLabel.setText(temp);
    }

    @FXML
    protected void setDash(DoctorDashboard doc) throws Exception {
        ArrayList<Appointment> app = doc.getAppointments();



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
            scene = new Scene(root, logoutButton.getScene().getWidth(), logoutButton.getScene().getHeight());
            window.setScene(scene);
            window.show();
        }
    }

    @FXML
    protected void deletePatientData() throws Exception {
        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION);
        deleteAlert.setTitle("Warning");
        deleteAlert.setHeaderText("You are about to delete this patient from the system");
        deleteAlert.setContentText("Are you sure you want to delete this patient?");

        if (deleteAlert.showAndWait().get() == ButtonType.OK) {

            changeScene(deletePatient, "DoctorDashboard.fxml", this.getID());
        }
    }

    @FXML
    protected void onDocDashClick() throws Exception {
        DoctorDashboard doc = new DoctorDashboard();
        doc.select(this.getID());
        logIn(backDocDash, "DoctorDashboard.fxml", this.getID(), doc);
    }

    @FXML
    protected void onDocVisitClick() throws Exception {
        changeScene(docVisit, "DoctorVisitSummary.fxml", this.getID());
    }

    @FXML
    protected void onDocPatientClick() throws Exception {
        changeScene(docPatients, "PatientSearch.fxml", this.getID());
        Patient pat = new Patient();
        ArrayList<Patient> patList = pat.select(this.getID());

    }

}
