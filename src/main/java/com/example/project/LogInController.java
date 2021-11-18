package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


public class LogInController {

    //initialize all variables

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
    public void loadFX(Hyperlink butt, String file) throws Exception { //this method basically just switches the window without transferring any info over
        root = FXMLLoader.load(Project.class.getResource(file));
        window = (Stage) butt.getScene().getWindow();
        scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    public void logIn (Button butt,String file, int id, PatientDashboard pat) throws Exception { //this switches the scene while transferring over key patient and doctor information
        FXMLLoader loader = new FXMLLoader(); //patient login basically
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        LoggedController cont = loader.getController();

        cont.setID(id);
        cont.setDoctorID(Connect.getPatientDoctor(id));
        cont.setName(id); //initializes controller
        cont.setDash(pat);


        Stage window = (Stage) butt.getScene().getWindow();
        Scene scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    public void staffLoginEvent (Button butt,String file, int id, DoctorDashboard doc) throws Exception { //this switches the scene for doctors and nurses and tranfers over their ids
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        LoggedStaffController cont = loader.getController();

        cont.setID(id); //initializes controller
        cont.setName(id);
        cont.setDash(doc);

        Stage window = (Stage) butt.getScene().getWindow();
        Scene scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onStaffLogin() throws Exception{ //this pretty much handles staff trying to log in

        if (staffID.getText().isBlank() || staffPassword.getText().isBlank()) {
            staffSignInLabel.setText("Please enter an email and password"); //if either textfield is blank
        }
        else {

            if (doctorRadio.isSelected()) { //if doctor is selected on screen
                int m;
                try {
                    Connect connect = new Connect();
                    m = connect.loginStaff(Integer.parseInt(staffID.getText()), staffPassword.getText()); //gets staff login id
                    if (m == -1) {
                        staffSignInLabel.setText("ID or password is incorrect"); //incorrect
                    }
                    else {
                        DoctorDashboard doc = new DoctorDashboard();
                        doc = doc.select(m); //correct
                        staffLoginEvent(staffLogin, "DoctorDashboard.fxml", m, doc);
                    }
                }
                catch (NumberFormatException e) {
                    staffSignInLabel.setText("Please enter an integer for the ID"); //when id isnt a number
                }
            }

            else if (nurseRadio.isSelected()) { // if nurse is selected on screen
                int m;
                try {
                    Connect connect = new Connect();
                    m = connect.loginNurse(Integer.parseInt(staffID.getText()), staffPassword.getText());
                    if (m == -1) {
                        staffSignInLabel.setText("ID or password is incorrect"); //incorrect
                    }
                    else {
                        DoctorDashboard doc = new DoctorDashboard();
                        doc = doc.select(m); //correct login
                        staffLoginEvent(staffLogin, "DoctorDashboard.fxml", m, doc);
                    }
                }
                catch (NumberFormatException e) {
                    staffSignInLabel.setText("Please enter an integer for the ID"); //when not number
                }
            }

            else {
                staffSignInLabel.setText("Please choose staff type"); //if a staff type isnt chosen on screen
            }

        }
    }

    @FXML
    protected void onSignUpClick() throws Exception{
        loadFX(signUp, "Patient Sign Up.fxml"); //loads signup screen
    }
    @FXML
    protected void onDoctorLoginClick() throws Exception{
        loadFX(doctorLogin, "Staff Login.fxml"); //loads doctor login screen
    }

    @FXML
    protected void onBackLogin() throws Exception{
        loadFX(backLogin, "login.fxml"); //when going back to login screen from any screen
    }



    @FXML
    protected void onLogin() throws Exception { //handles patient logins
        if (patientEmail.getText().isBlank() || patientPassword.getText().isBlank()) {
            signInLabel.setText("Please enter an email and password"); //if either field is empty
        }
        else {
            int m = Connect.loginPatient(patientEmail.getText(), patientPassword.getText()); //gets patient id
            if (m == -1) {
                signInLabel.setText("username or password is incorrect");
            }
            else {
                PatientDashboard pat = new PatientDashboard();
                pat.select(m); //gets dashboard

                logIn(loginButton, "Patient Dashboard.fxml", m, pat); //logs in patient
            }
        }

    }
}