package com.example.portal;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static jdk.net.Sockets.setOption;

public class Controller implements Initializable {

   private static final String URL = "jdbc:mysql://localhost:3306/portal_schema";
   private static final String USER = "root";
   private static final String PASSWORD = "kyllie12602";

   private final Image hideImage = new Image(String.valueOf(getClass().getResource("images/icons/hide.png")));
   private final Image viewImage = new Image(String.valueOf(getClass().getResource("images/icons/view.png")));

   ObservableList<String> departmentOptions = FXCollections.observableArrayList(
           "CAS - College of Arts and Sciences",
           "CCS - College of Computer Studies",
           "CIHM - College of International Tourism & Hospitality Management",
           "CON - College of Nursing",
           "COE - College of Engineering",
           "COED - College of Education",
           "CBA - College of Business & Accountancy"
   );

   ObservableList<String> employmentStatus = FXCollections.observableArrayList(
           "Part-Time",
           "Full-Time"
   );

   @FXML private AnchorPane mainPane;

   @FXML private AnchorPane bannerPane;
   @FXML private Pane loginCreateAccountPane, registerHaveAccountPane;
   @FXML private Hyperlink loginCreateAccountLink,loginHaveAccountLink;

   @FXML private AnchorPane inputPane;
   @FXML private Pane loginInputPane;
   @FXML private TextField loginEmailField, loginViewPasswordField;
   @FXML private PasswordField loginHidePasswordField;
   @FXML private ImageView loginPasswordState;
   @FXML private Button loginButton;
   @FXML private Hyperlink loginResetPasswordLink;

   private boolean isLoginPasswordHidden = true;

   @FXML private Pane registerInputPane;
   @FXML private TextField registerLastNameField, registerFirstNameField, registerMiddleInitialField, registerPhoneNumberField, registerEmailField, registerViewPasswordField;
   @FXML private ComboBox registerDepartmentBox, registerEmploymentBox;
   @FXML private PasswordField registerHidePasswordField;
   @FXML private ImageView registerPasswordState;
   @FXML private Button registerButton;

   private boolean isRegisterPasswordHidden = true;

   @FXML private AnchorPane forgotPasswordPane;
   @FXML private Pane forgotCenterPane;
   @FXML private TextField forgotEmailField, forgotViewOldPasswordField, forgotViewNewPasswordField;
   @FXML private PasswordField forgotHideOldPasswordField, forgotHideNewPasswordField;
   @FXML private ImageView forgotOldPasswordState, forgotNewPasswordState;
   @FXML private Button resetButton;
   @FXML private Hyperlink forgotReturnLink;

   private boolean isForgotOldPasswordHidden = true;
   private boolean isForgotNewPasswordHidden = true;

   @FXML private ImageView closeButton, minimizeButton;

   public static String lastNameDisplay;
   public static String firstNameDisplay;
   public static String middleInitialDisplay;
   public static String phoneNumberDisplay;
   public static String emailDisplay;
   public static String departmentDisplay;
   public static String employmentDisplay;

   @Override
   public void initialize(URL url, ResourceBundle resourceBundle) {

      Platform.runLater(() -> {
         Stage stage = (Stage) mainPane.getScene().getWindow();

         Authentication();
         NavigationHandler();
         PasswordHandler();
         WindowHandler(stage);

         setOption(new ComboBox[]{registerDepartmentBox, registerEmploymentBox}, new ObservableList[]{departmentOptions, employmentStatus});
      });
   }

   public void setOption(ComboBox<String>[] boxes, ObservableList<String>[] options) {
      for (int i = 0; i < boxes.length; i++) {
         boxes[i].setItems(options[i]);
      }
   }

   public void Authentication() {
      loginButton.setOnMouseClicked(_ -> {
         String email = loginEmailField.getText();
         String password = String.valueOf(loginHidePasswordField.getText());

         String sql = "SELECT * FROM professors WHERE email = ? AND password = ?";

         try {
            Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement statement = connect.prepareStatement(sql);

            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
               System.out.println("Login!");

               lastNameDisplay = resultSet.getString("lastName");
               firstNameDisplay = resultSet.getString("firstName");
               middleInitialDisplay = resultSet.getString("middleInitial");
               phoneNumberDisplay = resultSet.getString("phoneNumber");
               emailDisplay = resultSet.getString("email");
               departmentDisplay = resultSet.getString("department");
               employmentDisplay = resultSet.getString("employment");

               switchToSecondaryScene();

            } else {
               System.out.println("Wrong Credentials!");
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      });

      registerButton.setOnMouseClicked(_ -> {
         String lastName = registerLastNameField.getText();
         String firstName = registerFirstNameField.getText();
         String middleInitial = registerMiddleInitialField.getText();

         String phoneNumber = registerPhoneNumberField.getText();

         String email = registerEmailField.getText();
         String password = String.valueOf(registerHidePasswordField.getText());

         String department = (String) registerDepartmentBox.getValue();
         String employment = (String) registerEmploymentBox.getValue();

         String sql = "INSERT INTO professors (lastName, firstName, middleInitial, phoneNumber, email, password, department, employment) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

         if (lastName.isEmpty() || firstName.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() || department == null || employment == null) {
            showAlert();
         } else {
            try {
               Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
               PreparedStatement statement = connect.prepareStatement(sql);

               statement.setString(1, lastName);
               statement.setString(2, firstName);
               statement.setString(3, middleInitial);
               statement.setString(4, (phoneNumber));
               statement.setString(5, email);
               statement.setString(6, password);
               statement.setString(7, department);
               statement.setString(8, employment);
               statement.executeUpdate();

               System.out.println("Succesfull Created an Account!");

            } catch (SQLException e) {
               throw new RuntimeException(e);
            }
         }
      });

      resetButton.setOnMouseClicked(_ -> {
         String email = forgotEmailField.getText();
         String oldPassword = String.valueOf(forgotHideOldPasswordField.getText());
         String newPassword = String.valueOf(forgotHideNewPasswordField.getText());

         String sqlCompareOld = "SELECT * FROM professors WHERE email = ? AND password = ?";
         String sqlUpdate = "UPDATE professors SET password = ? WHERE email = ?";

         try {
            Connection connect = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement selectStatement = connect.prepareStatement(sqlCompareOld);
            PreparedStatement updateStatement = connect.prepareStatement(sqlUpdate);

            selectStatement.setString(1, email);
            selectStatement.setString(2, oldPassword);

            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
               updateStatement.setString(1, newPassword);
               updateStatement.setString(2, email);
               updateStatement.executeUpdate();

               System.out.println("Successfully Changed your Password!");
            } else {
               System.out.println("Error, email or password does not match within our database!");
            }
         } catch (SQLException e) {
            throw new RuntimeException(e);
         }
      });
   }

   public void NavigationHandler() {
      loginCreateAccountLink.setOnMouseClicked(_ -> {

         registerInputPane.setVisible(true);
         registerHaveAccountPane.setVisible(true);

         loginInputPane.setVisible(false);
         loginCreateAccountPane.setVisible(false);

         forgotPasswordPane.setVisible(false);
      });

      loginHaveAccountLink.setOnMouseClicked(_ -> {

         loginInputPane.setVisible(true);
         loginCreateAccountPane.setVisible(true);

         registerInputPane.setVisible(false);
         registerHaveAccountPane.setVisible(false);

         forgotPasswordPane.setVisible(false);
      });

      loginResetPasswordLink.setOnMouseClicked(_ -> {
         forgotPasswordPane.setVisible(true);

         loginInputPane.setVisible(false);
         loginCreateAccountPane.setVisible(false);

         registerInputPane.setVisible(false);
         registerHaveAccountPane.setVisible(false);

         bannerPane.setVisible(false);
      });

      forgotReturnLink.setOnMouseClicked(_ -> {

         loginInputPane.setVisible(true);
         loginCreateAccountPane.setVisible(true);

         bannerPane.setVisible(true);

         registerInputPane.setVisible(false);
         registerHaveAccountPane.setVisible(false);

         forgotPasswordPane.setVisible(false);
      });

   }

   public void PasswordHandler() {
      // Login
      loginPasswordState.setOnMouseClicked(_ -> {
         if (isLoginPasswordHidden) {
            loginPasswordState.setImage(viewImage);

            loginHidePasswordField.setVisible(false);

            loginViewPasswordField.setText(loginHidePasswordField.getText());
            loginViewPasswordField.setVisible(true);

            loginViewPasswordField.setOnKeyTyped(_ -> {
               loginHidePasswordField.setText(loginViewPasswordField.getText());
            });
         } else {
            loginPasswordState.setImage(hideImage);

            loginViewPasswordField.setVisible(false);

            loginHidePasswordField.setText(loginViewPasswordField.getText());
            loginHidePasswordField.setVisible(true);
         }

         isLoginPasswordHidden = !isLoginPasswordHidden;
      });

      // Register
      registerPasswordState.setOnMouseClicked(_ -> {
         if (isRegisterPasswordHidden) {
            registerPasswordState.setImage(viewImage);

            registerHidePasswordField.setVisible(false);

            registerViewPasswordField.setText(registerHidePasswordField.getText());
            registerViewPasswordField.setVisible(true);

            registerViewPasswordField.setOnKeyTyped(_ -> {
               registerHidePasswordField.setText(registerViewPasswordField.getText());
            });
         } else {
            registerPasswordState.setImage(hideImage);

            registerViewPasswordField.setVisible(false);

            registerHidePasswordField.setText(registerViewPasswordField.getText());
            registerHidePasswordField.setVisible(true);
         }

         isRegisterPasswordHidden = !isRegisterPasswordHidden;
      });

      // Forgot Password
      forgotOldPasswordState.setOnMouseClicked(_ -> {
         if (isForgotOldPasswordHidden) {
            forgotOldPasswordState.setImage(viewImage);

            forgotHideOldPasswordField.setVisible(false);

            forgotViewOldPasswordField.setText(forgotHideOldPasswordField.getText());
            forgotViewOldPasswordField.setVisible(true);

            forgotViewOldPasswordField.setOnKeyTyped(_ -> {
               forgotHideOldPasswordField.setText(forgotViewOldPasswordField.getText());
            });
         } else {
            forgotOldPasswordState.setImage(hideImage);

            forgotViewOldPasswordField.setVisible(false);

            forgotHideOldPasswordField.setText(forgotViewOldPasswordField.getText());
            forgotHideOldPasswordField.setVisible(true);
         }

         isForgotOldPasswordHidden = !isForgotOldPasswordHidden;
      });

      forgotNewPasswordState.setOnMouseClicked(_ -> {
         if (isForgotNewPasswordHidden) {
            forgotNewPasswordState.setImage(viewImage);

            forgotHideNewPasswordField.setVisible(false);

            forgotViewNewPasswordField.setText(forgotHideNewPasswordField.getText());
            forgotViewNewPasswordField.setVisible(true);

            forgotViewNewPasswordField.setOnKeyTyped(_ -> {
               forgotHideNewPasswordField.setText(forgotViewNewPasswordField.getText());
            });
         } else {
            forgotNewPasswordState.setImage(hideImage);

            forgotViewNewPasswordField.setVisible(false);

            forgotHideNewPasswordField.setText(forgotViewNewPasswordField.getText());
            forgotHideNewPasswordField.setVisible(true);
         }

         isForgotNewPasswordHidden = !isForgotNewPasswordHidden;
      });
   }

   public void WindowHandler(Stage stage) {
      closeButton.setOnMouseClicked(_ -> {
         stage.close();
      });

      minimizeButton.setOnMouseClicked(_ -> {
         stage.setIconified(true);
      });
   }

   private void switchToSecondaryScene() {
      try {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/dashboard.fxml"));
         Scene secondaryScene = new Scene(loader.load());

         Stage stage = (Stage) loginButton.getScene().getWindow();
         stage.setScene(secondaryScene);
         stage.setFullScreen(true);
      } catch (IOException e) {
         e.printStackTrace();
         showAlert();
      }
   }

   private void showAlert() {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("ERROR");
      alert.setHeaderText("MISSING INPUTS OR ERROR");
      alert.setContentText("You either have missing inputs or have input wrong credentials");
      alert.showAndWait();
   }

}