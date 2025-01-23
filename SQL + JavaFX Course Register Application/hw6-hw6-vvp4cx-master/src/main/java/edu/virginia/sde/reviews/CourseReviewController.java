package edu.virginia.sde.reviews;

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

public class CourseReviewController {

    @FXML
    private Label courseTitleLabel;

    @FXML
    private Label averageReviewLabel;

    @FXML
    private Label courseMnemonicLabel;

    @FXML
    private VBox reviewListContainer;

    @FXML
    private TextField reviewRatingField;

    @FXML
    private TextField reviewCommentField;

    @FXML
    private Button backButton;

    @FXML
    private Button addReviewButton;

    private Course selectedCourse;

    private int currentReviewId = -1;

    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }


    @FXML
    public void initialize() {
        if (selectedCourse != null) {
            courseTitleLabel.setText(selectedCourse.getSubject() + " " + selectedCourse.getNumber() + "     " + selectedCourse.getTitle());
            averageReviewLabel.setText("Average Review: " + String.format("%.2f", selectedCourse.getAverageReview()));
            populateReviews();
        }
    }


    public void setCourse(Course course) {
        this.selectedCourse = course;
        courseTitleLabel.setText(course.getTitle());
        courseMnemonicLabel.setText(course.getSubject() + " " + course.getNumber());
        try {
            double updatedAvgReview = ReviewDatabase.getAverageReviewForCourse(course.getId());
            if (updatedAvgReview != 0.0) {
                course.setAverageReview(String.format("%.2f", updatedAvgReview));
            } else {
                course.setAverageReview(null);
            }
            if (course.getAverageReview() == null || course.getAverageReview().isBlank()) {
                averageReviewLabel.setText("No reviews rated yet");
            } else {
                averageReviewLabel.setText("" + course.getAverageReview());
            }
        } catch (SQLException e) {
            System.err.println("Could not fetch average review rating" + e.getMessage());
            averageReviewLabel.setText("No ratings yet");
        }
        populateReviews();
    }


    private void populateReviews() {
        reviewListContainer.getChildren().clear();

        try {
            List<Review> reviews = ReviewDatabase.getReviewsForCourse(selectedCourse.getId());
            int currentUserId = User.getInstance().getUserId();
            boolean userHasReview = false;

            for (Review review : reviews) {
                HBox reviewBox = new HBox(10);
                reviewBox.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-radius: 5; -fx-background-color: white; -fx-background-radius: 5;");

                VBox reviewInfo = new VBox(5);
                Label ratingLabel = new Label("Rating: " + review.getRating());
                ratingLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
                Label timestampLabel = new Label("Timestamp: " + review.getTimestamp());
                timestampLabel.setStyle("-fx-font-size: 12px;");
                String commentText;
                if (review.getComment().isEmpty()) {
                    commentText = "No comment";
                } else {
                    commentText = review.getComment();
                }
                Label commentLabel = new Label("Comment: " + commentText);
                commentLabel.setStyle("-fx-font-size: 12px;");
                reviewInfo.getChildren().addAll(ratingLabel, timestampLabel, commentLabel);

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                Button deleteButton = new Button("Delete");
                deleteButton.setStyle("-fx-font-size: 12px;");
                deleteButton.setOnAction(event -> handleDeleteReview(review));

                reviewBox.getChildren().addAll(reviewInfo, spacer, deleteButton);
                reviewListContainer.getChildren().add(reviewBox);

                // Change to edit if user already has a review
                if (review.getUserId() == currentUserId) {
                    reviewRatingField.setText(String.valueOf(review.getRating()));
                    reviewCommentField.setText(review.getComment());
                    addReviewButton.setText("Edit Review");
                    addReviewButton.setOnAction(event -> handleEditReview());
                    currentReviewId = review.getId();
                    userHasReview = true;
                }
            }

            // Change back to add if no review (user could delete his own review)
            if (!userHasReview) {
                resetToAddReviewState();
            }

        } catch (SQLException e) {
            System.err.println("Couldn't fetch reviews" + e.getMessage());
        }
    }

    private void resetToAddReviewState() {
        addReviewButton.setText("Add Review");
        addReviewButton.setOnAction(event -> handleAddReview());
        reviewRatingField.clear();
        reviewCommentField.clear();
        currentReviewId = -1; // Reset the current review ID
    }



    @FXML
    private Label addReviewErrorLabel;



    /**
     * Handle adding a new review.
     */
    @FXML
    private void handleAddReview() {
        try {
            int userId = User.getInstance().getUserId();
            int rating = Integer.parseInt(reviewRatingField.getText().trim());
            if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be between 1 and 5.");

            String comment = reviewCommentField.getText().trim();
            long timestamp = System.currentTimeMillis();

            // Check if the user has already reviewed this course
            boolean hasReviewed = ReviewDatabase.hasUserReviewedCourse(selectedCourse.getId(), userId);
            if (hasReviewed) {
                throw new IllegalArgumentException("You have already reviewed this course.");
            }

            // Add the review to the database
            ReviewDatabase.addReview(selectedCourse.getId(), userId, rating, comment, timestamp);
            ReviewDatabase.updateCourseAverageReview(selectedCourse.getId());
            updateAverageReview();
            // Clear input fields
            reviewRatingField.clear();
            reviewCommentField.clear();

            // Refresh the review list
            populateReviews();

        } catch (NumberFormatException e) {
            addReviewErrorLabel.setText("Rating needs to be 1-5");
            addReviewErrorLabel.setVisible(true);
        } catch (IllegalArgumentException e) {
            addReviewErrorLabel.setText(e.getMessage());
            addReviewErrorLabel.setVisible(true);
        } catch (SQLException e) {
            addReviewErrorLabel.setText("Couldn't add review :/" + e.getMessage());
            addReviewErrorLabel.setVisible(true);
        }
    }


    private void handleDeleteReview(Review review) {
        try {
            int currentUserId = User.getInstance().getUserId();
            if (review.getUserId() != currentUserId) {
                throw new IllegalArgumentException("You can only delete your own reviews.");
            }
            ReviewDatabase.deleteReview(review.getId());
            populateReviews();
            updateAverageReview();
            resetToAddReviewState();
        } catch (IllegalArgumentException e) {
            addReviewErrorLabel.setText("You can only delete your own reviews.");
            addReviewErrorLabel.setVisible(true);
            System.out.println("You can only delete your own reviews.");
        } catch (SQLException e) {
            System.err.println("Error deleting review: " + e.getMessage());
        }
    }


    private void updateAverageReview() {
        try {
            double averageReview = ReviewDatabase.getAverageReviewForCourse(selectedCourse.getId());
            if (averageReview == 0.0) {
                selectedCourse.setAverageReview(null);
                averageReviewLabel.setText("No reviews rated yet");
            } else {
                String formattedAverage = String.format("%.2f", averageReview);
                selectedCourse.setAverageReview(formattedAverage);
                averageReviewLabel.setText("Average Rating: " + formattedAverage);
            }

            String averageReviewString;
            if (selectedCourse.getAverageReview() == null) {
                averageReviewString = "";
            } else {
                averageReviewString = selectedCourse.getAverageReview();
            }
            CourseDatabase.updateCourseAverageReviewInDatabase(selectedCourse.getId(), averageReviewString);
        } catch (SQLException e) {
            System.err.println("Couldn't update average review" + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/virginia/sde/reviews/course-search.fxml"));
            Parent courseSearch = loader.load();
            CourseSearchController searchController = loader.getController();
            String updatedAverageReview = averageReviewLabel.getText().replace("Average Rating: ", "").trim();
            searchController.updateCourseAverageReview(selectedCourse.getId(), updatedAverageReview);
            reviewRatingField.clear();
            reviewCommentField.clear();
            addReviewButton.setText("Add Review");
            addReviewButton.setOnAction(event -> handleAddReview());
            Stage currentStage = (Stage) backButton.getScene().getWindow();
            Scene courseSearchScene = new Scene(courseSearch, 1280, 720);
            currentStage.setScene(courseSearchScene);
            currentStage.setTitle("Course Search");
        } catch (IOException e) {
            System.err.println("Couldn't load course search page" + e.getMessage());
        }
    }

    @FXML
    private void handleEditReview() {
        try {
            if (currentReviewId == -1) {
                throw new IllegalArgumentException("no edit selected");
            }
            int userId = User.getInstance().getUserId();
            int rating = Integer.parseInt(reviewRatingField.getText().trim());
            if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be between 1 and 5.");
            String comment = reviewCommentField.getText().trim();
            long timestamp = System.currentTimeMillis();
            ReviewDatabase.editReview(selectedCourse.getId(), userId, rating, comment, timestamp);
            populateReviews();
            updateAverageReview();
        } catch (Exception e) {
            addReviewErrorLabel.setText(e.getMessage());
            addReviewErrorLabel.setVisible(true);
        }
    }


}


