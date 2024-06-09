package com.example.portal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Profile implements Initializable {

   @FXML private Pane mainPane;
   @FXML private Label nameDisplayCase, departmentDisplayCase, employmentDisplayCase, emailDisplayCase, phoneDisplayCase;

   @FXML private ImageView close, hide;
   @FXML private Pane homeButton;

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

         homeButton.setOnMouseClicked(_ -> {
            switchToSecondaryScene();
         });

         nameDisplayCase.setText(Controller.lastNameDisplay + ", " + Controller.firstNameDisplay + ", " + Controller.middleInitialDisplay);
         departmentDisplayCase.setText(Controller.departmentDisplay);
         employmentDisplayCase.setText(Controller.employmentDisplay);

         emailDisplayCase.setText(Controller.emailDisplay);
         phoneDisplayCase.setText(Controller.phoneNumberDisplay);
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
