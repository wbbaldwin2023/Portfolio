package edu.virginia.sde.reviews;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

//AI Agent: ChatGPT
//Prompt: help me with the first step of implementing a login screen + How should I write this to navigate to the new course search screen file

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in both fields.");
        } else {
            boolean isValidUser = UserDatabaseSetup.userValidation(username, password);
            if (isValidUser) {
                messageLabel.setText("Login successful!");
                messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
                UserDatabaseSetup.getUserId(username).ifPresent(userId -> {
                    User.getInstance().setUser(userId, username);});

                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("course-search.fxml"));
                    Parent courseSearchRoot = loader.load();

                    // Set up the new scene
                    Scene courseSearchScene = new Scene(courseSearchRoot, 1280, 720);

                    // Get the current stage and set the new scene
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(courseSearchScene);
                    stage.setTitle("Course Search");

                } catch (IOException e) {
                    e.printStackTrace();
                    messageLabel.setText("Error loading course search screen.");
                }
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        }
    }

    @FXML
    private void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Missing username or password.");
        } else if (password.length() < 8) {
            messageLabel.setText("Password needs to be at least 8 characters.");
        } else {
            boolean isRegistered = UserDatabaseSetup.makeNewUser(username, password);
            if (isRegistered) {
                messageLabel.setText("Registration successful! Please log in.");
                messageLabel.setTextFill(javafx.scene.paint.Color.GREEN);
            } else {
                messageLabel.setText("Registration failed. Username may already exist.");
            }
        }
    }

    public void closeProgram(ActionEvent event) {
        Platform.exit(); // Exit the application
    }
}
