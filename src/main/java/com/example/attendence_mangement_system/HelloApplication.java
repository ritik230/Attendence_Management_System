package com.example.attendence_mangement_system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.opencv.core.Core;


//import nu.pattern.OpenCV;
//import org.opencv.core.Core;
//import org.opencv.osgi.OpenCVNativeLoader;
//import nu.pattern.OpenCV;


import java.io.IOException;

import static org.opencv.core.Core.*;


public class HelloApplication extends Application {
    @Override

    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.show();


    }

    public static void main(String[] args) throws IOException {
        launch();

    }
}