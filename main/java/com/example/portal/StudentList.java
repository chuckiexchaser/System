package com.example.portal;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;

public class StudentList implements Initializable {

   private static final String URL = "jdbc:mysql://localhost:3306/portal_schema";
   private static final String USER = "root";
   private static final String PASSWORD = "kyllie12602";

   @FXML private ImageView close;
   @FXML private ImageView hide;
   @FXML private BorderPane mainPane;

   @FXML
   private ChoiceBox<String> semester;
   private String[] sem = {"1st", "2nd"};
   @FXML
   private ChoiceBox<String> Course;
   private String[] courses = {"BSIT", "BSCS"};
   @FXML
   private ChoiceBox<String> Year;
   private String[] years = {"1", "2","3","4"};
   @FXML
   private ChoiceBox<String> Section;
   private String[] secs = {"A", "B","C","D","E"};
   @FXML
   private ChoiceBox<String> statusONE;
   private String[] one = {"Passed","Failed"};

   @FXML TableView<Students> studentTable;
   @FXML TableColumn<Students, String> studentID;
   @FXML TableColumn<Students, String> studentName;

   @FXML private TextField studentIDField, studentNameField;
   @FXML private Button addStudentButton;
   @FXML private Pane homeButton;

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

      studentID.setCellValueFactory(new PropertyValueFactory<Students, String>("studentID"));
      studentName.setCellValueFactory(new PropertyValueFactory<Students, String>("studentName"));

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


         String sql = "INSERT INTO students (name, studentID, course, yearLevel, section) VALUES (?, ?, ?, ?, ?)";

         Course.getItems().addAll(courses);
         semester.getItems().addAll(sem);
         Year.getItems().addAll(years);
         Section.getItems().addAll(secs);
         statusONE.getItems().addAll(one);

         addStudentButton.setOnMouseClicked(_ -> {
            String studentID = studentIDField.getText();
            String studentName = studentNameField.getText();
            String course = Course.getValue();
            String yearLevel = Year.getValue();
            String section = Section.getValue();

            try {
               Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
               PreparedStatement statement = connect.prepareStatement(sql);

               statement.setString(1, studentName);
               statement.setString(2, studentID);
               statement.setString(3, course);
               statement.setString(4, yearLevel);
               statement.setString(5, section);
               statement.executeUpdate();

               addStudent(studentID, studentName, course, yearLevel, section);

            } catch (SQLException e) {
               throw new RuntimeException(e);
            }
         });

      });
   }

   public void addStudent(String studentID, String studentName, String course, String year, String section) {
      ObservableList<Students> list = FXCollections.observableArrayList(
              new Students(studentID, studentName, course, year, section)
      );

      studentTable.setItems(list);
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