package com.example.project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Project extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Project.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
        stage.getIcons().add(new Image("https://spng.pngfind.com/pngs/s/44-440660_doctor-plus-logo-png-green-plus-transparent-png.png"));
    }

    public static void main(String[] args) {
        Connect.Connect();

        launch();
    }
}