package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class SignUpController {

    @FXML
    private Button patientSignUp, firstAppointment, submitInsurance, submitPharmacy;

    @FXML
    private TextField signUpFirst, signUpLast, signUpEmail, signUpPhone, signUpAddress;

    @FXML
    private TextField firstPharmacy, firstPharmPhone, firstPharmAddress;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private Label firstInsName, firstInsPhone, firstInsAddress, firstInsMemberID, signUpLabel, patAppLabel, insuranceWarning, pharmacyWarning;

    @FXML
    private DatePicker signUpBday, choiceDate;

    @FXML
    private ChoiceBox<String> choiceTime;

    @FXML
    private ChoiceBox<String> doctorChoice;

    @FXML
    private TextArea doctorInfo;

    @FXML
    private Hyperlink backLogin;

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

    public void popupApp (String file) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        SignUpController appSet = loader.getController();
        appSet.populateAppointment();

        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL);
        box.initStyle(StageStyle.UNDECORATED);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();
    }

    @FXML
    public void populateAppointment() {
        ArrayList<Doctor> doc = new ArrayList<>();
        ArrayList<String> docName = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Doctor");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                doc.add(new Doctor(rs.getString("name"), rs.getInt("doctorID"), rs.getString("password")));
                docName.add(rs.getString("name"));
            }

            ObservableList<String> docList = FXCollections.observableArrayList(docName);
            doctorChoice.setItems(docList);

        }
        catch (Exception e) {

        }



    }

    @FXML
    protected void onBackLogin() throws Exception{
        root = FXMLLoader.load(Project.class.getResource("login.fxml"));
        window = (Stage) backLogin.getScene().getWindow();
        scene = new Scene(root, backLogin.getScene().getWidth(), backLogin.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }



    @FXML
    protected void onEnterAppointment() throws Exception {
        Stage box = (Stage) firstAppointment.getScene().getWindow();
        Appointment app = new Appointment();
        try {
            LocalDate now = LocalDate.now();
            if (choiceDate.getValue().isAfter(now)) {
                app.insert(choiceTime.getValue().toString(), choiceDate.getValue().toString(), Integer.valueOf(doctorChoice.getValue().toString()), this.getID(), 1);

                box.close();
            }

            else {
                patAppLabel.setText("Please enter a future date");
            }
        }
        catch (Exception e) {
            patAppLabel.setText("Please enter valid entries for each field");
        }
    }

    @FXML
    protected void onEnterInsurance() throws Exception {
        Stage box = (Stage) submitInsurance.getScene().getWindow();
        try {
            Insurance ins = new Insurance();
            ins.setName(firstInsName.getText());
            ins.setPhoneNumber(Integer.parseInt(firstInsPhone.getText()));
            ins.setMember(Integer.parseInt(firstInsMemberID.getText()));
            ins.setAddress(firstInsAddress.getText());

            ins.insert(ins, this.getID());

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
            Pharmacy pharm = new Pharmacy();
            pharm.setAddress(firstPharmAddress.getText());
            pharm.setPhoneNumber(Integer.parseInt(firstPharmPhone.getText()));
            pharm.setName(firstPharmacy.getText());

            pharm.insert(pharm, this.getID());

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


                int m = Connect.loginPatient(signUpEmail.getText(), signUpPassword.getText());
                this.setID(m);

                popupApp("firstAppointment.fxml");

                popup("insurance.fxml");

                popup("pharmacy.fxml");

                root = FXMLLoader.load(Project.class.getResource("login.fxml"));
                window = (Stage) patientSignUp.getScene().getWindow();
                scene = new Scene(root, patientSignUp.getScene().getWidth(), patientSignUp.getScene().getHeight());
                window.setScene(scene);
                window.show();

            }
        }
        catch (NullPointerException e){
            signUpLabel.setText("Please enter a valid birthday");
        }

    }
}
