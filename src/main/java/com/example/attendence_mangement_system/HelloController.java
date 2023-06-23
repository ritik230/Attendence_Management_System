package com.example.attendence_mangement_system;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class HelloController {
    @FXML
    private Label welcomeText;
    private Button student , management, faculty,back,managementloginbutton;
    private Stage  stage;
    private Scene scene;
    FXMLLoader fxmlLoader;
    @FXML
    private Button updatestudent , removestudent;

    @FXML


    protected void onstudentbuttonclick( ActionEvent event) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("studentlogin.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("studentlogin.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    };

    @FXML
    protected void onmanagementbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("managementlogin.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("managementlogin.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    };

  @FXML
    protected void onfacultybuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("facultylogin.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("facultylogin.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    };
  @FXML
    protected void onbackbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    };


    @FXML
    protected void onaddstudentbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("registrationform.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("registrationform.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    };
    @FXML
    protected void onupdatestudentbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("updatestudent.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("updatestudent.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    };
@FXML
    protected void onremovestudentbuttonclick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("removestudent.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("removestudent.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    };




    }
