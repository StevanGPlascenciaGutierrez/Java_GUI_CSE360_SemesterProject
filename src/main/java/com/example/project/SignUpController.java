package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static com.example.project.Connect.conn;

public class SignUpController {

    //initializes variables

    @FXML
    private Button patientSignUp, firstAppointment, submitInsurance, submitPharmacy;

    @FXML
    private TextField signUpFirst, signUpLast, signUpEmail, signUpPhone, signUpAddress;

    @FXML
    private TextField firstPharmacy, firstPharmPhone, firstPharmAddress;

    @FXML
    private PasswordField signUpPassword;

    @FXML
    private Label signUpLabel, patAppLabel, insuranceWarning, pharmacyWarning;

    @FXML
    private DatePicker signUpBday, choiceDate;

    @FXML
    private ChoiceBox<String> choiceTime;

    @FXML
    private ComboBox<String> doctorChoice;

    @FXML
    private TextArea doctorInfo;

    @FXML
    private Hyperlink backLogin;

    @FXML
    private TextField firstInsName, firstInsPhone, firstInsAddress, firstInsMemberID;

    private Parent root;
    private Scene scene;
    private Stage window;
    private int userID;

    //used to transfer information between screens

    protected void setID(int id) {
        this.userID = id;
    }

    protected int getID() {
        return userID;
    }

    public void popup (String file) throws IOException { //this is for the signup event so that you cant close or change size of screens
        FXMLLoader loader = new FXMLLoader(); //it switches screens as a popup
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        SignUpController appSet = loader.getController();
        appSet.setID(this.getID()); //loads controller

        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL);
        box.initStyle(StageStyle.UNDECORATED);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();
    }

    public void popupApp (String file) throws IOException { //this switches the screen to firstapplointment when signing up
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource(file));
        Parent root = loader.load();

        SignUpController appSet = loader.getController();
        appSet.setID(this.getID()); //loads controller
        appSet.populateAppointment();

        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL); //cant close or change size
        box.initStyle(StageStyle.UNDECORATED);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();
    }

    @FXML
    public void onDoctorChange() throws Exception { //this handles appointment time combobox for doctors

        //initializes arraylist of times to cross check with doctor times

        ArrayList<String> times = new ArrayList<>();
        times.add(LocalTime.of(8, 0).toString());
        times.add(LocalTime.of(10, 0).toString());
        times.add(LocalTime.of(12, 0).toString());
        times.add(LocalTime.of(14, 0).toString());
        times.add(LocalTime.of(16, 0).toString());



        Appointment app = new Appointment();
        try {
            ArrayList<String> timeList = app.selectByDName(doctorChoice.getValue(), choiceDate.getValue().toString()); //list of times

            for (int i = 0; i < times.size(); i++) {
                for (int j = 0; j < timeList.size(); j++) {
                    if (times.get(i).equals(timeList.get(j))) {
                        times.remove(i); //removes equal elements to only populate times that doctor doesnt have
                    }
                }
            }

            choiceTime.getItems().setAll(times); //sets only times that doctor doesnt have
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    public void populateAppointment() { //populates appointment screens on signup
        ArrayList<Doctor> doc = new ArrayList<>();
        ArrayList<String> docName = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Doctor");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) { //gets doctor object
                doc.add(new Doctor(rs.getString("name"), rs.getInt("doctorID"), rs.getString("password")));
                docName.add(rs.getString("name"));
            }

            ObservableList<String> docList = FXCollections.observableArrayList(docName); //list of doctors is initialized
            doctorChoice.setItems(docList); //sets table

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }



    }

    @FXML
    protected void onBackLogin() throws Exception{ //goes back to login screen
        root = FXMLLoader.load(Project.class.getResource("login.fxml"));
        window = (Stage) backLogin.getScene().getWindow();
        scene = new Scene(root, backLogin.getScene().getWidth(), backLogin.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }



    @FXML
    protected void onEnterAppointment() throws Exception { //when going to firstAppointment screen
        Stage box = (Stage) firstAppointment.getScene().getWindow();
        Appointment app = new Appointment();
        try {
            LocalDate now = LocalDate.now();
            if (choiceDate.getValue().isAfter(now)) { //only lets you put times after now
                int doctorID = Doctor.getDoctorID(doctorChoice.getValue());
                app.insert(choiceTime.getValue().toString(), choiceDate.getValue().toString(), doctorID, this.getID(), Nurse.getNurseID(doctorID));
                Patient pat = new Patient();
                pat.updateDoctor(this.getID(), doctorID); //updates doctor info

                box.close();
            }

            else {
                patAppLabel.setText("Please enter a future date"); //in case that patient enters invalid date
            }
        }
        catch (Exception e) {
            patAppLabel.setText("Please enter valid entries for each field");
        }
    }

    @FXML
    protected void onEnterInsurance() throws Exception { //when going to insurance on signup
        Stage box = (Stage) submitInsurance.getScene().getWindow();
        try {
            Insurance ins = new Insurance();
            ins.setName(firstInsName.getText());
            ins.setPhoneNumber(Integer.parseInt(firstInsPhone.getText()));
            ins.setMember(Integer.parseInt(firstInsMemberID.getText()));
            ins.setAddress(firstInsAddress.getText());

            ins.insert(ins, this.getID()); //prompts to enter insurance information

            box.close();
        }
        catch (Exception e) {
            insuranceWarning.setText("Please enter valid entries for each field"); //when things arent entered for each field
        }

    }

    @FXML
    protected void onEnterPharmacy() throws Exception { //when going to pharmacy screen of signup event
        Stage box = (Stage) submitPharmacy.getScene().getWindow();
        try {
            Pharmacy pharm = new Pharmacy();
            pharm.setAddress(firstPharmAddress.getText());
            pharm.setPhoneNumber(Integer.parseInt(firstPharmPhone.getText()));
            pharm.setName(firstPharmacy.getText()); //prompts user to enter pharmacy information

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
                signUpLabel.setText("Please enter valid entries for each field"); //makes sure all fields filled on signup
            }
            else {

                Connect.signUp(signUpFirst.getText(), signUpLast.getText(), signUpBday.getValue().toString(), signUpEmail.getText(),
                        signUpPassword.getText(), signUpAddress.getText(), Long.parseLong(signUpPhone.getText())); //signup event


                int m = Connect.loginPatient(signUpEmail.getText(), signUpPassword.getText()); //gets patient id
                this.setID(m);

                popupApp("firstAppointment.fxml"); //popup screen for appointment

                popup("insurance.fxml"); //popup for insurance

                popup("pharmacy.fxml"); //popup for pharmacy

                root = FXMLLoader.load(Project.class.getResource("login.fxml"));
                window = (Stage) patientSignUp.getScene().getWindow(); //goes back to login screen
                scene = new Scene(root, patientSignUp.getScene().getWidth(), patientSignUp.getScene().getHeight());
                window.setScene(scene);
                window.show();

            }
        }
        catch (NullPointerException e){
            signUpLabel.setText("Please enter a valid birthday"); //in case of invalid birthday entry
        }

    }
}
