package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CourseSearchController {

    @FXML
    private VBox courseListContainer;

    @FXML
    private TextField subjectField;

    @FXML
    private TextField numberField;

    @FXML
    private TextField titleField;

    private ObservableList<Course> courses = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        CourseDatabase.initializeDatabase();
        ReviewDatabase.initializeReviewDatabase();
        refreshCourses();
    }

    public void refreshCourses() {
        List<Course> allCourses = CourseDatabase.getAllCourses();
        courses.setAll(allCourses);
        populateCourses();
    }


    private void populateCourses() {
        courseListContainer.getChildren().clear(); // Clear the previous entries

        for (Course course : courses) {
            HBox courseBox = new HBox(10);
            courseBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-radius: 5; -fx-background-color: white; -fx-background-radius: 5;");

            VBox courseInfo = new VBox(5);
            Label courseTitle = new Label(course.getSubject() + " " + course.getNumber() + ": " + course.getTitle());
            courseTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            Label courseReview = new Label("Avg. Review: " + course.getAverageReview());
            courseReview.setStyle("-fx-font-size: 14px;");
            courseInfo.getChildren().addAll(courseTitle, courseReview);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button viewDetails = new Button("View Details");
            viewDetails.setStyle("-fx-font-size: 14px;");
            viewDetails.setOnAction(event -> showCourseDetails(course));

            courseBox.getChildren().addAll(courseInfo, spacer, viewDetails);
            courseListContainer.getChildren().add(courseBox);

        }
    }

    //AI Agent: ChatGPT
    //Prompt: (current code context) + I want to transition to a course reviews page when the view details button is pressed
    private void showCourseDetails(Course course) {
        try {
            // Load the course-reviews.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/virginia/sde/reviews/course-reviews.fxml"));
            Parent reviewsRoot = loader.load();

            // Get the controller for the course-reviews.fxml
            CourseReviewController reviewController = loader.getController();
            reviewController.setCourse(course); // Pass the selected course

            // Switch to the new scene
            Stage currentStage = (Stage) courseListContainer.getScene().getWindow();
            Scene reviewScene = new Scene(reviewsRoot, 1280, 720);
            currentStage.setScene(reviewScene);
            currentStage.setTitle("Course Reviews - " + course.getTitle());
        } catch (IOException e) {
            System.err.println("Error loading Course Reviews Scene: " + e.getMessage());
        }
    }


    @FXML
    private void handleSearch() {

        String courseSubject = subjectField.getText().trim();
        String courseNumber = numberField.getText().trim();
        String courseTitle = titleField.getText().trim();
        String searchSubject = null;
        String searchNumber = null;
        String searchTitle = null;
        if (!courseSubject.isEmpty()) {
            searchSubject = courseSubject;
        }
        if (!courseNumber.isEmpty()) {
            searchNumber = courseNumber;
        }
        if (!courseTitle.isEmpty()) {
            searchTitle = courseTitle;
        }
        List<Course> searchResults = CourseDatabase.searchCourses(searchSubject, searchNumber, searchTitle);
        courses.setAll(searchResults);
        populateCourses();
    }



    @FXML
    private Button logoutButton;

    @FXML
    private void handleLogout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/virginia/sde/reviews/login.fxml"));
            Parent loginRoot = loader.load();
            Stage currentStage = (Stage) logoutButton.getScene().getWindow();
            Scene loginScene = new Scene(loginRoot);
            currentStage.setScene(loginScene);
            currentStage.setTitle("Login");
        } catch (IOException e) {
            System.err.println("Error couldn't load login scene: " + e.getMessage());
        }
    }


    @FXML
    private TextField addSubjectField;

    @FXML
    private TextField addNumberField;

    @FXML
    private TextField addTitleField;

    @FXML
    private TextField addReviewField;

    @FXML
    private Label addErrorMessage;

    @FXML
    private void handleAddCourse() {
        try {
            String subject = addSubjectField.getText().trim().toUpperCase();
            int number = Integer.parseInt(addNumberField.getText().trim());
            String title = addTitleField.getText().trim();
            Double avgReview = null;

            if (subject.length() < 2 || subject.length() > 4) throw new IllegalArgumentException("2-4 letters needed for Subject");
            if (String.valueOf(number).length() != 4) throw new IllegalArgumentException("4 digit number only");
            if (title.isEmpty() || title.length() > 50) throw new IllegalArgumentException("Title needs 1-50 characters");

            // Add the course to the database
            CourseDatabase.addCourse(subject, number, title, null);

            // Clear input fields
            addSubjectField.clear();
            addNumberField.clear();
            addTitleField.clear();
            addErrorMessage.setText("");

            // Refresh course list
            List<Course> updatedCourses = CourseDatabase.getAllCourses();
            courses.setAll(updatedCourses);
            populateCourses();
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate course")) {
                addErrorMessage.setText("Can't add duplicate class!");
                addErrorMessage.setVisible(true);
            } else {
                addErrorMessage.setText("Error adding course!");
                addErrorMessage.setVisible(true);
            }
        } catch (NumberFormatException e) {
            addErrorMessage.setText("Course number must be numeric");
            addErrorMessage.setStyle("-fx-text-fill: red;");
            addErrorMessage.setVisible(true);
        } catch (IllegalArgumentException e) {
            addErrorMessage.setText(e.getMessage());
            addErrorMessage.setStyle("-fx-text-fill: red;");
            addErrorMessage.setVisible(true);
        } catch (Exception e) {
            addErrorMessage.setText("Unexpected error: " + e.getMessage());
            addErrorMessage.setStyle("-fx-text-fill: red;");
            addErrorMessage.setVisible(true);
        }
    }

    public void updateCourseAverageReview(int courseId, String averageReview) {
        for (Course course : courses) {
            if (course.getId() == courseId) {
                course.setAverageReview(averageReview);
                break;
            }
        }
        populateCourses();
    }


    @FXML
    private void handleMyReviews() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/virginia/sde/reviews/my-reviews.fxml"));
            Parent myReviews = loader.load();
            MyReviewsController myReviewsController = loader.getController();

            int currentUserId = User.getInstance().getUserId();
            System.out.print("test" + currentUserId);
            myReviewsController.setUserId(currentUserId);


            Scene myReviewsScene = new Scene(myReviews, 1280, 720);
            Stage currentStage = (Stage) courseListContainer.getScene().getWindow();
            currentStage.setScene(myReviewsScene);
            currentStage.setTitle("My Reviews");
        } catch (IOException e) {
            System.err.println("Couldn't go to my reviews scene" + e.getMessage());
        }
    }

}
