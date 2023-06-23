module com.example.attendence_mangement_system {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;


    requires java.sql;





    requires org.bytedeco.javacpp;
    requires org.bytedeco.opencv;


    opens com.example.attendence_mangement_system to javafx.fxml;

    exports com.example.attendence_mangement_system;
}