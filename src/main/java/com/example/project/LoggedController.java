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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.project.Connect.conn;

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
    private ComboBox<String> visitDate, patAppDoc, patAppTime;

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

    @FXML
    private TableView<Prescription> prescriptionTable;

    @FXML
    private TableColumn<Prescription, String> preTableName, preTableFrom, preTableTo, preTableDosage, preTableDesc;

    @FXML
    private TextField newPreName, newPreDosage, newPreDesc;

    @FXML
    private DatePicker newPreStart, newPreEnd;

    @FXML
    private Button addNewPre, deletePre;

    @FXML
    private Label newPreWarning;

    @FXML
    private TableView<Allergy> allergyTable;

    @FXML
    private TableColumn<Allergy, String> allergyNameCol, allergyDescCol;

    @FXML
    private TextField newAllergyName, newAllergyDesc;

    @FXML
    private Button newAllergy;

    @FXML
    private TableView<HealthIssues> issueTable;

    @FXML
    private TableColumn<HealthIssues, String> issueNameCol, issueDescCol, issueDateCol;

    @FXML
    private Label allergyWarning;

    @FXML
    private Label visitDoctor, visitBasicName, visitBasicHeight, visitBasicWeight, visitBasicBMI;

    @FXML
    private Label visitHR, visitBT, visitBP, visitRR;

    @FXML
    private TableView<HealthIssues> newIssueTable;

    @FXML
    private TableColumn<HealthIssues, String> newIssueCol, newIssueDate, newIssueDescription;

    @FXML
    private TextArea doctorNote;

    @FXML
    private TextField newMessage;

    @FXML
    private Hyperlink sendMessage;

    @FXML
    private TableView<Message> messageTable;

    @FXML
    private TableColumn<Message, String> docMessageCol, patMessageCol;

    private Parent root;
    private Scene scene;
    private Stage window;

    private int userID;
    private int userDoctor;

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
        nameLabel.setText("Patient: " + arr.get(0));
        patName.setText(arr.get(0));
        patPhone.setText(arr.get(1));
        patAddress.setText(arr.get(2));
        patEmail.setText(arr.get(3));
        patDoctor.setText("Doctor: " + arr.get(4));
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
            System.out.println(e.getMessage());
        }

    }

    @FXML
    protected void onInsertNewAllergy() {
        try {
            Allergy all = new Allergy();
            all.setType(newAllergyName.getText());
            all.setDescription(newAllergyDesc.getText());
            all.insert(all, this.getID());

            this.setHealthHistory(this.getID());
        }
        catch (Exception e) {
            allergyWarning.setText("Please enter valid fields for each entry");
            System.out.println(e.getMessage());
        }
    }

    protected void setHealthHistory (int patientID) {
        HealthHistory health = new HealthHistory().select(patientID);
        ObservableList<Allergy> allergyList = FXCollections.observableArrayList(health.getAllergies());
        ObservableList<HealthIssues> issuesList = FXCollections.observableArrayList(health.getIssues());
        try  {
            allergyNameCol.setCellValueFactory(new PropertyValueFactory<Allergy, String>("type"));
            allergyDescCol.setCellValueFactory(new PropertyValueFactory<Allergy, String>("description"));
            allergyTable.setItems(allergyList);
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        try  {
            issueNameCol.setCellValueFactory(new PropertyValueFactory<HealthIssues, String>("name"));
            issueDateCol.setCellValueFactory(new PropertyValueFactory<HealthIssues, String>("date"));
            issueDescCol.setCellValueFactory(new PropertyValueFactory<HealthIssues, String>("description"));
            issueTable.setItems(issuesList);
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    protected void onPatHealthClick() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("Patient Health History.fxml"));
        Parent root = loader.load();

        LoggedController cont = loader.getController();

        cont.setID(this.getID());
        cont.setDoctorID(Connect.getPatientDoctor(this.getID()));
        cont.setHealthHistory(this.getID());

        Stage window = (Stage) patHealthHistClick.getScene().getWindow();
        Scene scene = new Scene(root, patHealthHistClick.getScene().getWidth(), patHealthHistClick.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onDeletePrescription() {

    }

    @FXML
    protected void onNewPrescription() {
        Prescription pre = new Prescription();
        try {
            pre.setName(newPreName.getText());
            pre.setStartDate(newPreStart.getValue().toString());
            pre.setEndDate(newPreEnd.getValue().toString());
            pre.setDosage(Double.parseDouble(newPreDosage.getText()));
            pre.setDescription(newPreDesc.getText());
            pre.insert(pre, this.getID());

            ArrayList<Prescription> preList = pre.select(this.getID());
            this.setPrescriptions(preList);

        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            newPreWarning.setText("Please enter valid entries for every field");
        }

    }

    @FXML
    protected void setPrescriptions(ArrayList<Prescription> preList) throws Exception {

        ObservableList<Prescription> temp = FXCollections.observableArrayList(preList);

        try {
            preTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
            preTableFrom.setCellValueFactory(new PropertyValueFactory<>("startDate"));
            preTableTo.setCellValueFactory(new PropertyValueFactory<>("endDate"));
            preTableDosage.setCellValueFactory(new PropertyValueFactory<>("dosage"));
            preTableDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            prescriptionTable.setItems(temp);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onPreClick() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("PrescriptionHistory.fxml"));
        root = loader.load();

        LoggedController cont = loader.getController();

        cont.setID(this.getID());
        cont.setDoctorID(this.getDoctorID());

        Prescription pre = new Prescription();
        ArrayList<Prescription> preList = pre.select(this.getID());
        cont.setPrescriptions(preList);

        window = (Stage) prescriptionClick.getScene().getWindow();
        scene = new Scene(root, prescriptionClick.getScene().getWidth(), prescriptionClick.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onDateChange() {
        try {
            VisitSummary visit = new VisitSummary().selectVisitSummary(this.getID(), visitDate.getValue().toString());
            visitDoctor.setText(Connect.getDoctorName(Connect.getPatientDoctor(this.getID())));

            ObservableList<HealthIssues> issueList = FXCollections.observableArrayList(visit.getHealthIssues());
            ArrayList<String> arr = Connect.getUser(this.getID());

            visitBasicName.setText(arr.get(0));
            visitBasicHeight.setText(Double.toString(visit.getHeight()));
            visitBasicWeight.setText(Double.toString(visit.getWeight()));
            visitBasicBMI.setText(Double.toString(visit.getBMI()));

            visitBP.setText(Double.toString(visit.getVitals().getBloodPressure()));
            visitHR.setText(Double.toString(visit.getVitals().getHeartRate()));
            visitRR.setText(Double.toString(visit.getVitals().getRespiratoryRate()));
            visitBT.setText(Double.toString(visit.getVitals().getBodyTemp()));

            newIssueCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            newIssueDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            newIssueDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            newIssueTable.setItems(issueList);

            doctorNote.setText(visit.getDoctorNote());

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void setSummary(ArrayList<VisitSummary> visitArr) {
        ArrayList<String> dateArr = new ArrayList<String>();

        for (VisitSummary vis : visitArr) {
            dateArr.add(vis.getDate());
        }

        ObservableList<String> dateList = FXCollections.observableArrayList(dateArr);

        visitDate.setItems(dateList);
    }

    @FXML
    protected void onVisitClick() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("Visit Summary.fxml"));
        root = loader.load();

        LoggedController cont = loader.getController();

        cont.setID(this.getID());
        cont.setDoctorID(this.getDoctorID());
        VisitSummary visit = new VisitSummary();
        ArrayList<VisitSummary> visitArr;
        visitArr = visit.selectVisitSummary(this.getID());
        cont.setSummary(visitArr);

        window = (Stage) visitSumButt.getScene().getWindow();
        scene = new Scene(root, visitSumButt.getScene().getWidth(), visitSumButt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }

    @FXML
    protected void onSendMessage() {
        Message.insertMessage(newMessage.getText(), this.getID(), this.getDoctorID());
        loadChat(Message.getChat(this.getID(), this.getDoctorID()));
    }

    protected void loadChat(int chatNum) {

        try {
            ArrayList<Message> arr = new Message().selectMessage(chatNum);
            for (Message mess : arr){
                String temp = mess.getContent();
                int t = temp.indexOf(" ");
                if (temp.substring(0, t+1).equals("Doctor: ")) {
                    mess.setSender(mess.getContent());
                    mess.setContent(" ");
                }

            }

            ObservableList<Message> list = FXCollections.observableArrayList(arr);

            patMessageCol.setCellValueFactory(new PropertyValueFactory<>("content"));
            docMessageCol.setCellValueFactory(new PropertyValueFactory<>("sender"));

            messageTable.setItems(list);
        }

        catch (Exception e) {

        }
    }

    @FXML
    protected void onMessagesClick() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("messages.fxml"));
        Parent root = loader.load();

        LoggedController cont = loader.getController();

        cont.setID(this.getID());
        cont.setDoctorID(this.getDoctorID());
        cont.loadChat(Message.getChat(this.getID(), this.getDoctorID()));

        Stage window = (Stage) toMessages.getScene().getWindow();
        Scene scene = new Scene(root, toMessages.getScene().getWidth(), toMessages.getScene().getHeight());
        window.setScene(scene);
        window.show();

        cont.hideHeader();

    }

    protected void hideHeader() {
        Pane header = (Pane) messageTable.lookup("TableHeaderRow");
        header.setVisible(false);
        messageTable.setLayoutY(-header.getHeight());
        messageTable.autosize();
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
        cont.setDoctorID(this.getDoctorID());

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
        cont.setDoctorID(this.getDoctorID());

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
        cont.setDoctorID(this.getDoctorID());

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

    @FXML
    public void onAppComboChange() throws Exception {
        ArrayList<String> times = new ArrayList<>();
        times.add(LocalTime.of(8, 0).toString());
        times.add(LocalTime.of(10, 0).toString());
        times.add(LocalTime.of(12, 0).toString());
        times.add(LocalTime.of(14, 0).toString());
        times.add(LocalTime.of(16, 0).toString());



        Appointment app = new Appointment();
        try {
            ArrayList<String> timeList = app.selectByDName(patAppDoc.getValue(), patAppDate.getValue().toString());

            for (int i = 0; i < times.size(); i++) {
                for (int j = 0; j < timeList.size(); j++) {
                    if (times.get(i).equals(timeList.get(j))) {
                        times.remove(i); //removes equal elements to only populate times that doctor doesnt have
                    }
                }
            }

            patAppTime.getItems().setAll(times);
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

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
            patAppDoc.setItems(docList);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onAppointmentSub() throws Exception {
        Appointment app = new Appointment();
        LocalDate today = LocalDate.now();
        LocalDate appointDate = patAppDate.getValue();

        try {
            LocalDate now = LocalDate.now();
            if (patAppDate.getValue().isAfter(now)) {
                int doctorID = Doctor.getDoctorID(patAppDoc.getValue());
                app.insert(patAppTime.getValue().toString(), patAppDate.getValue().toString(), doctorID, this.getID(), Nurse.getNurseID(doctorID));
                Patient pat = new Patient();
                pat.updateDoctor(this.getID(), doctorID);

                changeScene(patAppSubmit, "PatientDashboard.fxml", this.getID());
            }

            else {
                patAppLabel.setText("Please enter valid entries for each field");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    protected void onAppButtClick() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Project.class.getResource("PatientAppointment.fxml"));
        Parent root = loader.load();

        LoggedController cont = loader.getController();

        cont.setID(this.getID());
        cont.setDoctorID(Connect.getPatientDoctor(this.getID()));
        cont.populateAppointment();

        Stage window = (Stage) appButt.getScene().getWindow();
        Scene scene = new Scene(root, appButt.getScene().getWidth(), appButt.getScene().getHeight());
        window.setScene(scene);
        window.show();
    }
}
