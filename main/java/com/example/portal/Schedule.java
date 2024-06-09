package com.example.portal;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Schedule implements Initializable {

   @FXML private Pane homeButton;
   @FXML private AnchorPane mainPane;

   @FXML private ImageView closebtn, hidebtn;

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

      Platform.runLater(() -> {

         Stage stage = (Stage) mainPane.getScene().getWindow();

         closebtn.setOnMouseClicked(event -> {
            stage.close();
         });
         hidebtn.setOnMouseClicked(event -> {
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
