package edu.virginia.sde.reviews;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class MyReviewsController {

    @FXML
    private VBox reviewListContainer;

    @FXML
    private Button backButton;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
        populateReviews();
    }

    @FXML
    public void initialize() {

    }

    private void populateReviews() {
        reviewListContainer.getChildren().clear();

        try {
            List<Review> reviews = ReviewDatabase.getReviewsByUser(userId);
            System.out.println(reviews.size());
            if (reviews.isEmpty()) {
                System.out.println("No reviews found for user ID: " + userId);
            }

            for (Review review : reviews) {

                Button reviewButton = new Button(review.getCourseMnemonic() + ": " + review.getRating() + " stars");
                reviewButton.setStyle("-fx-font-size: 14px; -fx-padding: 10; -fx-background-color: #e8e8e8; -fx-border-color: lightgray;");
                reviewButton.setOnAction(event -> goToCourseReviewScene(review.getCourseId()));

                reviewListContainer.getChildren().add(reviewButton);
            }
        } catch (SQLException e) {
            System.err.println("Couldn't get reviews " + e.getMessage());
        }
    }

    private void goToCourseReviewScene(int courseId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/virginia/sde/reviews/course-reviews.fxml"));
            Parent courseReviewRoot = loader.load();
            CourseReviewController reviewController = loader.getController();
            Course course = CourseDatabase.getCourseById(courseId);
            reviewController.setCourse(course);
            Stage currentStage = (Stage) reviewListContainer.getScene().getWindow();
            Scene courseReviewScene = new Scene(courseReviewRoot, 1280, 720);
            currentStage.setScene(courseReviewScene);
            currentStage.setTitle("Course Reviews - " + course.getTitle());
        } catch (IOException e) {
            System.err.println("Couldn't navigate to the course review scene " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Couldn't get course by id" + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/virginia/sde/reviews/course-search.fxml"));
            Parent courseSearch = loader.load();
            Scene courseSearchScene = new Scene(courseSearch, 1280, 720);
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            currentStage.setScene(courseSearchScene);
            currentStage.setTitle("Course Search");
        } catch (IOException e) {
            System.err.println("Couldn't go back to the course search screen" + e.getMessage());
        }
    }
}

