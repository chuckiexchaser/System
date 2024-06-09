package com.example.portal;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Attendance implements Initializable {
   @FXML
   private Label welcomeText;
   @FXML private ImageView close;
   @FXML private ImageView hide;
   @FXML private BorderPane mainPane;

   @FXML
   protected void onHelloButtonClick() {
      welcomeText.setText("Welcome to JavaFX Application!");
   }

   @FXML
   private ChoiceBox<String> course;
   private String[] courses = {"BSIT","BSCE","BSA", "BSN","BSCS","BSBA","BSE","BSP"};

   @FXML
   private ChoiceBox<String> yearlevel;
   private String[] year = {"1st Year", "2nd Year", "3rd Year", "4th Year"};

   @FXML
   private ChoiceBox<String> section;
   private String[] sec = {"A", "B","C","D","E"};

   @FXML private Pane homeButton;

   @FXML
   private void imageView(ActionEvent event) {
      Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
      stage.close();
   }

   private void stage() {
   }

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

      course.getItems().addAll(courses);
      yearlevel.getItems().addAll(year);
      section.getItems().addAll(sec);

      Platform.runLater(() -> {

         Stage stage = (Stage) mainPane.getScene().getWindow();

         close.setOnMouseClicked(event -> {
            stage.close();
         });
         hide.setOnMouseClicked(event -> {
            stage.setIconified(true);
         });

         homeButton.setOnMouseClicked(_ -> {
            switchToSecondaryScene();
         });
      });
   }

   private void switchToSecondaryScene() {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/dashboard.fxml"));
         Scene scene = new Scene(loader.load());

         Stage stage = (Stage) homeButton.getScene().getWindow();
         stage.setScene(scene);
         stage.setFullScreen(true);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
