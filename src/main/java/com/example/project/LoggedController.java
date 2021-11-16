package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggedController {

    @FXML
    Label patAppLabel, nameLabel, patPhone, patName, patEmail, patAddress, patDoctor, insName, insPhone, insID, insAddress;

    @FXML
    Label pharmName, pharmAddress, pharmPhone, immuneWarning;

    @FXML
    private Hyperlink backPatDash, addNewImm;
    @FXML
    private Button appButt, patAppSubmit, submitNewInsurance, submitNewPatient, logoutButton, toMessages, visitSumButt, patHealthHistClick, prescriptionClick;

    @FXML
    private Button submitImm;

    @FXML
    private TableView<Immunization> patImmune;

    @FXML
    private TableColumn<Immunization, String> patVaccine, patVaccDate, patDescription;

    @FXML
    private ChoiceBox<String> visitDate, patAppDoc, patAppTime;

    @FXML
    private DatePicker patAppDate, newImmDate;

    @FXML
    private TextArea patDocDesc;

    @FXML
    private TextField newImmName, newImmDesc;

    @FXML
    private TextField editInsName, editInsMemberID, editInsAddress, editInsPhone;

    @FXML
    private TextField newFirst, newLast, newPhone, newAddress, newEmail;

    @FXML
    private Label insWarning, patWarning;

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
        cont.setDoctorID(Connect.getPatientDoctor(id));


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
        insAddress.setText(ins.getAddress());
        insID.setText(Integer.toString(ins.getMember()));

        pharmName.setText(pharm.getName());
        pharmAddress.setText(pharm.getAddress());
        pharmPhone.setText(Integer.toString(pharm.getPhoneNumber()));

        ObservableList<Immunization> immune = FXCollections.observableArrayList(imm);
        try  {
            patVaccine.setCellValueFactory(new PropertyValueFactory<Immunization, String>("type"));
            patVaccDate.setCellValueFactory(new PropertyValueFactory<Immunization, String>("date"));
            patDescription.setCellValueFactory(new PropertyValueFactory<Immunization, String>("description"));
            patImmune.setItems(immune);
        }
        catch (NullPointerException e) {

        }

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
        VisitSummary visit = new VisitSummary();
        ArrayList<VisitSummary> visitArr;
        visitArr = visit.selectVisitSummary(this.getID());
        ArrayList<String> dateArr = new ArrayList<String>();

        for (VisitSummary vis : visitArr) {
            dateArr.add(vis.getDate());
        }

        ObservableList<String> dateList = FXCollections.observableArrayList(dateArr);

        visitDate.setItems(dateList);
    }

    @FXML
    protected void onAppButtClick() throws Exception{
        changeScene(appButt, "PatientAppointment.fxml", this.getID());
    }

    @FXML
    protected void onAppointmentSub() throws Exception {
        Appointment app = new Appointment();
        LocalDate today = LocalDate.now();
        LocalDate appointDate = patAppDate.getValue();

        if (appointDate != null && !appointDate.isBefore(today)) {
            try {
                String doc = patAppDoc.toString();
                String docDesc = patDocDesc.getText();
                app.insert(patAppTime.toString(), patAppDate.toString() ,this.getDoctorID(), this.getID(), 1);
                changeScene(patAppSubmit, "Patient Dashboard.fxml", this.getID());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        else {
            patAppLabel.setText("Please enter a valid date");
        }
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

        Patient pat = new Patient();

        try {
            pat.setPhoneNum(Integer.parseInt(newPhone.getText()));
            pat.setEmail(newEmail.getText());
            pat.setAddress(newAddress.getText());
            pat.setName(newFirst.getText() + " " + newLast.getText());

            pat.update(pat, this.getID());

            box.close();
        }
        catch (Exception e) {
            patWarning.setText("Please enter valid entries for each field");
        }


    }

    @FXML
    protected void onEditPatient() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("EditPatient.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoggedController cont = loader.getController();
        cont.setID(this.getID());

        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();

        setName(this.getID());

    }

    @FXML
    protected void onSubmitInsurance() throws Exception {
        Stage box = (Stage) submitNewInsurance.getScene().getWindow();

        try {
            Insurance ins = new Insurance();
            ins.setAddress(editInsAddress.getText());
            ins.setMember(Integer.parseInt(editInsMemberID.getText()));
            ins.setPhoneNumber(Integer.parseInt(editInsPhone.getText()));
            ins.setName(editInsName.getText());

            ins.update(ins, this.getID());


            box.close();
        }
        catch (Exception e) {
            insWarning.setText("Please enter valid entries for each field");
        }

    }

    @FXML
    protected void onEditInsurance() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("EditInsurance.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoggedController cont = loader.getController();
        cont.setID(this.getID());

        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();

        PatientDashboard pat = new PatientDashboard();
        pat.select(this.getID());
        setDash(pat);

    }

    @FXML
    protected void onNewImmunization() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("Immunization.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LoggedController cont = loader.getController();
        cont.setID(this.getID());

        Stage box = new Stage();
        scene = new Scene(root);
        box.initModality(Modality.APPLICATION_MODAL);
        box.setResizable(false);
        box.setScene(scene);
        box.showAndWait();

        PatientDashboard pat = new PatientDashboard();
        pat.select(this.getID());
        setDash(pat);

    }

    @FXML
    protected void onSubmitNewImm() throws Exception {
        Stage box = (Stage) submitImm.getScene().getWindow();
        Immunization newImm = new Immunization();
        newImm.setDate(newImmDate.getValue().toString());
        newImm.setType(newImmName.getText());
        newImm.setDescription(newImmDesc.getText());
        Immunization.insert(newImm, this.getID());

        box.close();
    }


}
