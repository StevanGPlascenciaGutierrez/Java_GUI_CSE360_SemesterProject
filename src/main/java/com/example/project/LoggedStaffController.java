package com.example.project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.project.Connect.conn;

public class LoggedStaffController {

    @FXML
    private Hyperlink backDocDash, backPatSearch;

    @FXML
    private Button deletePatient, docPatients, docVisit, logoutButton, searchButton;

    @FXML
    private Label nameLabel, insName, insPhone, insAddress, insID, pharmName, pharmAddress, pharmPhone;

    @FXML
    private Label patIDLabel, patNameLabel, patAddressLabel, patEmailLabel, patPhoneLabel;

    @FXML
    private TableView <Appointment> patAppointTable;

    @FXML
    private TableColumn <Appointment, String> patNameCol, patDateCol, patTimeCol;

    @FXML
    private TableView <Patient> patSearchTable;

    @FXML
    private TableColumn <Patient, String> patSearchID, patSearchName, patSearchLink;

    @FXML
    private TextField searchBar;

    @FXML
    private TableView<Immunization> patImmune;

    @FXML
    private TableColumn<Immunization, String> patVaccDate, patDescription, patVaccine;

    @FXML
    private ComboBox<Patient> patientComboBox;

    @FXML
    private Label visitorID, visitIssueWarning;

    @FXML
    private TextField patVisitHeight, patVisitWeight, patVisitBMI, newHeartRate, newBodyTemp, newBloodPressure, newRespirationRate;

    @FXML
    private TextArea doctorNoteText;

    @FXML
    private TextField newHealthIssue, newIssueDescription;

    @FXML
    private Button enterNewIssue, submitVisit;

    @FXML
    private TableView<HealthIssues> insertIssueTable;

    @FXML
    private TableColumn<HealthIssues, String > insertIssueCol, insertDescriptionCol, insertDateCol;

    private Parent root;
    private Scene scene;
    private Stage window;

    private int userID;

    private boolean type;

    protected void setID(int id) {
        this.userID = id;
    }

    protected int getID() {
        return userID;
    }

    @FXML
    protected void setDoctorView(PatientDashboard pat, int id) throws Exception {
        ArrayList<Immunization> imm;
        Pharmacy pharm;
        Insurance ins;

        ins = pat.getInsurance();
        pharm = pat.getPharmacy();

        insName.setText(ins.getName());
        insPhone.setText(Integer.toString(ins.getPhoneNumber()));
        insAddress.setText(ins.getAddress());
        insID.setText(Integer.toString(ins.getMember()));

        pharmName.setText(pharm.getName());
        pharmAddress.setText(pharm.getAddress());
        pharmPhone.setText(Integer.toString(pharm.getPhoneNumber()));

        ArrayList<String> arr = Connect.getUser(id);

        patIDLabel.setText("Patient ID: " + Integer.toString(id));
        patNameLabel.setText(arr.get(0));
        patAddressLabel.setText(arr.get(2));
        patEmailLabel.setText(arr.get(3));
        patPhoneLabel.setText(arr.get(1));

        try {
            imm = pat.getImmunizations();
            ObservableList<Immunization> immune = FXCollections.observableArrayList(imm);
            patVaccine.setCellValueFactory(new PropertyValueFactory<Immunization, String>("type"));
            patVaccDate.setCellValueFactory(new PropertyValueFactory<Immunization, String>("date"));
            patDescription.setCellValueFactory(new PropertyValueFactory<Immunization, String>("description"));
            patImmune.setItems(immune);
        }
        catch (Exception e) {

        }

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
        cont.setDash(doc);

        window = (Stage) butt.getScene().getWindow();
        scene = new Scene(root, butt.getScene().getWidth(), butt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void setName(int id) throws SQLException {
        String docSQL = "SELECT name FROM Doctor WHERE doctorID = ?";
        String temp;
        PreparedStatement stmt = conn.prepareStatement(docSQL);
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();

        temp = rs.getString("name");

        rs.close();

        nameLabel.setText(temp);
    }

    @FXML
    protected void setNurseName(int id) throws SQLException {
        String docSQL = "SELECT name FROM Nurse WHERE nurseID = ?";
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

        ObservableList<Appointment> appoint = FXCollections.observableArrayList(app);
        try  {
            patNameCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("name"));
            patDateCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("date"));
            patTimeCol.setCellValueFactory(new PropertyValueFactory<Appointment, String>("time"));
            patAppointTable.setItems(appoint);
        }
        catch (NullPointerException e) {

        }

    }

    @FXML
    protected void setSearch(ObservableList<Patient> pat) throws Exception {

        try  {
            patSearchID.setCellValueFactory(new PropertyValueFactory<>("ID"));
            patSearchName.setCellValueFactory(new PropertyValueFactory<>("name"));
            patSearchLink.setCellValueFactory(new PropertyValueFactory<>("link"));
            patSearchTable.setItems(pat);

        }
        catch (NullPointerException e) {

        }

    }

    @FXML
    protected void onSearch() {
        ObservableList<Patient> pat = patSearchTable.getItems();
        FilteredList<Patient> filteredData = new FilteredList<>(pat, b -> true);
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(searchData -> {
                if (newValue.isEmpty() || newValue == null || newValue.isBlank()) {
                    return true;
                }

                String key = newValue.toLowerCase();

                if (searchData.getName().toLowerCase().indexOf(key) > -1) {
                    return true;
                }
                else if (Integer.toString(searchData.getID()).indexOf(key) > -1) {
                    return true;
                }
                else {
                    return false;
                }
            });
        } );

        SortedList<Patient> newList = new SortedList<>(filteredData);
        newList.comparatorProperty().bind(patSearchTable.comparatorProperty());
        patSearchTable.setItems(newList);

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
            Patient pat = new Patient();
            pat.delete(this.getID());
            Stage box = (Stage) deletePatient.getScene().getWindow();
            box.setUserData(null);
            box.close();
        }
    }

    @FXML
    protected void onBackPatSearchClick() throws Exception {
        Stage box = (Stage) backPatSearch.getScene().getWindow();
        box.close();
    }

    @FXML
    protected void onDocDashClick() throws Exception {
        DoctorDashboard doc = new DoctorDashboard();
        doc.select(this.getID());
        logIn(backDocDash, "DoctorDashboard.fxml", this.getID(), doc);
    }

    @FXML
    protected void onSubmitNewVisitSummary() {
        try {
            VisitSummary newVisit = new VisitSummary();
            newVisit.setBMI(Double.parseDouble(patVisitBMI.getText()));
            newVisit.setHeight(Double.parseDouble(patVisitHeight.getText()));
            newVisit.setWeight(Double.parseDouble(patVisitWeight.getText()));
            newVisit.setDate(LocalDate.now().toString());
            newVisit.setDoctorNote(doctorNoteText.getText());
            Vitals newVitals = new Vitals();
            newVitals.setBloodPressure(Double.parseDouble(newBloodPressure.getText()));
            newVitals.setHeartRate(Double.parseDouble(newHeartRate.getText()));
            newVitals.setRespiratoryRate(Double.parseDouble(newRespirationRate.getText()));
            newVitals.setBodyTemp(Double.parseDouble(newBodyTemp.getText()));

            newVisit.setVitals(newVitals);

            try {
                List<HealthIssues> list = insertIssueTable.getItems();

                ArrayList<HealthIssues> arr = new ArrayList<>();

                for (HealthIssues h : list) {
                    arr.add(h);
                }

                newVisit.setHealthIssues(arr);

            }
            catch (NullPointerException e) {

            }

            newVisit.insert(newVisit, Integer.parseInt(visitorID.getText()));
            changeScene(submitVisit, "DoctorDashboard.fxml", this.getID());

        }
        catch (Exception e) {
            Alert warning = new Alert(Alert.AlertType.ERROR);
            warning.setTitle("Warning");
            warning.setHeaderText("Invalid entries");
            warning.setContentText("Please eneter valid entries for every field");
            warning.showAndWait();
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onComboPatient() {
        visitorID.setText(Integer.toString(patientComboBox.getValue().getID()));
        insertIssueTable.getItems().clear();
    }

    protected void populateVisit(int id) {
        ArrayList<Patient> patList = new Patient().select(id);
        ArrayList<String> patName = new ArrayList<>();

        for (Patient pat : patList) {
            patName.add(pat.getName());
        }

        try {

            patientComboBox.setItems(FXCollections.observableList(patList));




        }
        catch(NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    protected void onEnterHealthIssue() {
        ObservableList<HealthIssues> list = null;
        try {
            list = insertIssueTable.getItems();
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        try {
            LocalDate now = LocalDate.now();
            HealthIssues health = new HealthIssues();
            health.setName(newHealthIssue.getText());
            health.setDescription(newIssueDescription.getText());
            health.setDate(now.toString());
            list.add(health);

            insertIssueCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            insertDateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
            insertDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

            insertIssueTable.setItems(list);
        }
        catch (NullPointerException e) {
            visitIssueWarning.setText("Please enter valid entries");
            System.out.println(e.getMessage());
        }

    }

    @FXML
    protected void onDocVisitClick() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("DoctorVisitSummary.fxml"));
        root = loader.load();

        LoggedStaffController cont = loader.getController();

        cont.setID(this.getID());
        cont.populateVisit(this.getID());

        window = (Stage) docVisit.getScene().getWindow();
        scene = new Scene(root, docVisit.getScene().getWidth(), docVisit.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onDocPatientClick() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("PatientSearch.fxml"));
        root = loader.load();

        LoggedStaffController cont = loader.getController();

        cont.setID(this.getID());

        Patient pat = new Patient();
        ArrayList<Patient> patList = pat.select(this.getID());
        ObservableList<Patient> patTab = FXCollections.observableArrayList(patList);

        cont.setSearch(patTab);

        window = (Stage) docPatients.getScene().getWindow();
        scene = new Scene(root, docPatients.getScene().getWidth(), docPatients.getScene().getHeight());
        window.setScene(scene);
        window.show();

    }


}
