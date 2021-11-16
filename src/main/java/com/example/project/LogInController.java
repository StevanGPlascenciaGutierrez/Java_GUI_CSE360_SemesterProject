package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class LogInController {


    @FXML
    private Hyperlink signUp, backLogin, doctorLogin;
    @FXML
    private Button loginButton, staffLogin;

    @FXML
    private TextField patientEmail, staffID;

    @FXML
    private PasswordField patientPassword, staffPassword;

    @FXML
    private Label signInLabel, staffSignInLabel;

    @FXML
    private RadioButton doctorRadio, nurseRadio;

    private Parent root;
    private Scene scene;
    private Stage window;


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

    public void logIn (Button butt,String file, int id, PatientDashboard pat) throws Exception {
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

    public void staffLoginEvent (Button butt,String file, int id, DoctorDashboard doc) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        LoggedStaffController cont = loader.getController();

        cont.setID(id);
        cont.setName(id);
        cont.setDash(doc);

        Stage window = (Stage) butt.getScene().getWindow();
        Scene scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void nurseLoginEvent(Button butt,String file, int id, DoctorDashboard doc) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        LoggedStaffController cont = loader.getController();

        cont.setID(id);
        cont.setNurseName(id);
        cont.setDash(doc);

        Stage window = (Stage) butt.getScene().getWindow();
        Scene scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onStaffLogin() throws Exception{

        if (staffID.getText().isBlank() || staffPassword.getText().isBlank()) {
            staffSignInLabel.setText("Please enter an email and password");
        }
        else {

            if (doctorRadio.isSelected()) {
                int m;
                try {
                    Connect connect = new Connect();
                    m = connect.loginStaff(Integer.parseInt(staffID.getText()), staffPassword.getText());
                    if (m == -1) {
                        staffSignInLabel.setText("ID or password is incorrect");
                    }
                    else {
                        DoctorDashboard doc = new DoctorDashboard();
                        doc.select(m);
                        staffLoginEvent(staffLogin, "DoctorDashboard.fxml", m, doc);
                    }
                }
                catch (NumberFormatException e) {
                    staffSignInLabel.setText("Please enter an integer for the ID");
                }
            }

            else if (nurseRadio.isSelected()) {
                int m;
                try {
                    Connect connect = new Connect();
                    m = connect.loginNurse(Integer.parseInt(staffID.getText()), staffPassword.getText());
                    if (m == -1) {
                        staffSignInLabel.setText("ID or password is incorrect");
                    }
                    else {
                        DoctorDashboard doc = new DoctorDashboard();
                        doc.nurseSelect(m);
                        nurseLoginEvent(staffLogin, "DoctorDashboard.fxml", m, doc);
                    }
                }
                catch (NumberFormatException e) {
                    staffSignInLabel.setText("Please enter an integer for the ID");
                }
            }

            else {
                staffSignInLabel.setText("Please choose staff type");
            }

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
    protected void onLogin() throws Exception {
        if (patientEmail.getText().isBlank() || patientPassword.getText().isBlank()) {
            signInLabel.setText("Please enter an email and password");
        }
        else {
            int m = Connect.loginPatient(patientEmail.getText(), patientPassword.getText());
            if (m == -1) {
                signInLabel.setText("username or password is incorrect");
            }
            else {
                PatientDashboard pat = new PatientDashboard();
                pat.select(m);

                logIn(loginButton, "Patient Dashboard.fxml", m, pat);
            }
        }

    }
}