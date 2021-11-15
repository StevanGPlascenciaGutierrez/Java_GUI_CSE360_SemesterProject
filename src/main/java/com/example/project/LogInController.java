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
    private Button patientSignUp, loginButton, staffLogin, firstAppointment, submitInsurance, submitPharmacy;

    @FXML
    private TextField patientEmail, staffID, signUpFirst, signUpLast, signUpEmail, signUpPhone, signUpAddress;

    @FXML
    private PasswordField patientPassword, staffPassword, signUpPassword;

    @FXML
    private Label signInLabel, staffSignInLabel, signUpLabel, patAppLabel, insuranceWarning, pharmacyWarning;

    @FXML
    private DatePicker signUpBday, choiceDate;

    @FXML
    private ComboBox doctorChoice, choiceTime;

    @FXML
    private TextArea doctorInfo;

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

    public void popup (String file) throws IOException {
        Parent root = FXMLLoader.load(Project.class.getResource(file));
        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL);
        box.initStyle(StageStyle.UNDECORATED);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();
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
    protected void onStaffLogin() throws Exception{

        if (staffID.getText().isBlank() || staffPassword.getText().isBlank()) {
            staffSignInLabel.setText("Please enter an email and password");
        }
        else {

            if (doctorRadio.isSelected()) {
                int m;
                try {
                    m = Connect.loginStaff(Integer.parseInt(staffID.getText()), staffPassword.getText());
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
    protected void onEnterAppointment() throws Exception {
        Stage box = (Stage) firstAppointment.getScene().getWindow();
        Appointment app = new Appointment();
        try {
            app.insert(choiceTime.toString(), choiceDate.toString(), Integer.valueOf(doctorChoice.toString()), 1, 1);
            box.close();
        }
        catch (Exception e) {
            patAppLabel.setText("Please enter valid entries for each field");
        }
    }

    @FXML
    protected void onEnterInsurance() throws Exception {
        Stage box = (Stage) submitInsurance.getScene().getWindow();
        try {
            box.close();
        }
        catch (Exception e) {
            insuranceWarning.setText("Please enter valid entries for each field");
        }

    }

    @FXML
    protected void onEnterPharmacy() throws Exception {
        Stage box = (Stage) submitPharmacy.getScene().getWindow();
        try {
            box.close();
        }
        catch (Exception e) {
            pharmacyWarning.setText("Please enter valid entries for each field");
        }
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

                Connect.signUp(signUpFirst.getText(), signUpLast.getText(), signUpBday.getValue().toString(), signUpEmail.getText(),
                        signUpPassword.getText(), signUpAddress.getText(), Long.parseLong(signUpPhone.getText()));

                popup("firstAppointment");

                popup("insurance");

                popup("pharmacy");

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