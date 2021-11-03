package com.example.project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private Hyperlink signUp, backLogin, doctorLogin;

    @FXML
    protected void onSignUpClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Patient Sign Up.fxml"));
        Stage window = (Stage) signUp.getScene().getWindow();
        window.setScene(new Scene(root, 750, 500));
    }
    @FXML
    protected void onDoctorLoginClick() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("Staff Login.fxml"));
        Stage window = (Stage) doctorLogin.getScene().getWindow();
        window.setScene(new Scene(root, 750, 500));
    }

    @FXML
    protected void onBackLogin() throws Exception{
        Parent root = FXMLLoader.load(Project.class.getResource("login.fxml"));
        Stage window = (Stage) backLogin.getScene().getWindow();
        window.setScene(new Scene(root, 750, 500));
    }
}