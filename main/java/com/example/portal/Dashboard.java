package com.example.portal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
   @FXML private AnchorPane mainPane;

   @FXML private Label lastNameLabel;
   @FXML private ImageView close, hide;
   @FXML private Hyperlink studentListLink;

   @FXML private Hyperlink profileLink, attendanceLink, classSchedLink;

   String home = "scenes/dashboard.fxml";
   String profileScene = "scenes/profile.fxml";
   String studentScene = "scenes/studentsList.fxml";
   String attendanceScene = "scenes/attendance.fxml";
   String schedScene = "scenes/schedule.fxml";

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {
      Platform.runLater(() -> {

         Stage stage = (Stage) mainPane.getScene().getWindow();


         close.setOnMouseClicked(_ -> {
            stage.close();
         });

         hide.setOnMouseClicked(_ -> {
            stage.setIconified(true);
         });


         lastNameLabel.setText(Controller.lastNameDisplay);

         studentListLink.setOnMouseClicked(_ -> {
            System.out.println("Hello");

            switchToSecondaryScene(studentScene, studentListLink);
         });

         profileLink.setOnMouseClicked(_ -> {
            switchToSecondaryScene(profileScene, profileLink);
         });

         attendanceLink.setOnMouseClicked(_ -> {
            switchToSecondaryScene(attendanceScene, attendanceLink);
         });

         classSchedLink.setOnMouseClicked(_ -> {
            switchToSecondaryScene(schedScene, classSchedLink);
         });
      });
   }

   private void switchToSecondaryScene(String FXMLScene, Node targetNode) {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLScene));
         Scene scene = new Scene(loader.load());

         Stage stage = (Stage) targetNode.getScene().getWindow();
         stage.setScene(scene);
         stage.setFullScreen(true);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
