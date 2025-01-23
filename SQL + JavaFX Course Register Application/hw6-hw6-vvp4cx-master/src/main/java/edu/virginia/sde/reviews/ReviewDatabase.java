package edu.virginia.sde.reviews;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class ReviewDatabase {

    private static final String DB_URL = "jdbc:sqlite:course_reviews.db";


    public static void initializeReviewDatabase() {
        try (Connection connection = DriverManager.getConnection(DB_URL);
             Statement statement = connection.createStatement()) {

            String createTableSQL = """
                    CREATE TABLE IF NOT EXISTS reviews (
                          id INTEGER PRIMARY KEY AUTOINCREMENT,
                          course_id INTEGER NOT NULL,
                          user_id INTEGER NOT NULL,
                          rating INTEGER NOT NULL,
                          comment TEXT,
                          timestamp TEXT NOT NULL,
                          FOREIGN KEY (course_id) REFERENCES Courses(CourseId) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
                          UNIQUE (course_id, user_id)
                      ); 
                    """;

            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Couldn't initialize review database due to error: " + e.getMessage());
        }
    }

    public static List<Review> getReviewsForCourse(int courseId) throws SQLException {
        String query = "SELECT id, course_id, user_id, rating, comment, timestamp FROM reviews WHERE course_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Review> reviews = new ArrayList<>();
            while (resultSet.next()) {
                String timestampString = resultSet.getString("timestamp");
                Timestamp timestamp = Timestamp.valueOf(timestampString);

                Review review = new Review(
                        resultSet.getInt("id"),
                        resultSet.getInt("course_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment"),
                        timestamp
                );
                reviews.add(review);
            }
            return reviews;
        }
    }


    public static void addReview(int courseId, int userId, int rating, String comment, long timestamp) throws SQLException {
        String checkDuplicateSQL = "SELECT COUNT(*) FROM reviews WHERE course_id = ? AND user_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement checkStatement = connection.prepareStatement(checkDuplicateSQL)) {
            checkStatement.setInt(1, courseId);
            checkStatement.setInt(2, userId);
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                throw new SQLException("Only one review per user");
            }
        }

        String insertSQL = "INSERT INTO reviews (course_id, user_id, rating, comment, timestamp) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {

            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, rating);
            preparedStatement.setString(4, comment);
            Timestamp sqlTimestamp = new Timestamp(timestamp);
            preparedStatement.setString(5, sqlTimestamp.toString());
            preparedStatement.executeUpdate();
        }
    }


    public static void deleteReview(int reviewId) throws SQLException {
        String deleteSQL = "DELETE FROM reviews WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {

            preparedStatement.setInt(1, reviewId);
            preparedStatement.executeUpdate();
        }
    }

    public static double getAverageReviewForCourse(int courseId) throws SQLException {
        String query = "SELECT AVG(rating) AS average_rating FROM reviews WHERE course_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getDouble("average_rating");
            } else {
                return 0.0;
            }
        }
    }

    public static boolean hasUserReviewedCourse(int courseId, int userId) throws SQLException {
        String query = "SELECT COUNT(*) AS review_count FROM reviews WHERE course_id = ? AND user_id = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("review_count") > 0;
            }
        }

        return false;
    }

    public static void updateCourseAverageReview(int courseId) throws SQLException {
        String updateSQL = """
            UPDATE Courses
            SET AvgReview = (
                SELECT AVG(rating)
                FROM reviews
                WHERE course_id = ?
            )
            WHERE CourseId = ?
        """;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, courseId);
            preparedStatement.executeUpdate();
        }
    }



    public static void editReview(int courseId, int userId, int rating, String comment, long timestamp) throws SQLException {
        String updateSQL = """
            UPDATE reviews
            SET rating = ?, comment = ?, timestamp = ?
            WHERE course_id = ? AND user_id = ?
        """;

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setInt(1, rating);
            preparedStatement.setString(2, comment);
            Timestamp sqlTimestamp = new Timestamp(timestamp);
            preparedStatement.setString(3, sqlTimestamp.toString());
            preparedStatement.setInt(4, courseId);
            preparedStatement.setInt(5, userId);

            int changedRows = preparedStatement.executeUpdate();
            if (changedRows == 0) {
                throw new SQLException("Couldn't edit review");
            }
        }
    }

    //AI Agent: ChatGPT
    //Prompt: (context of current code) + do I need to create a new SQL database based on reviews for each user or is it already possible to pull reviews from different courses based on userid with my current code?
    public static List<Review> getReviewsByUser(int userId) throws SQLException {
        String query = """
        SELECT rev.id, rev.course_id, rev.user_id, rev.rating, rev.comment, rev.timestamp,
               course.CourseSubject, course.CourseNumber, course.CourseTitle
        FROM reviews rev
        JOIN Courses course ON rev.course_id = course.CourseId
        WHERE rev.user_id = ?
    """;


        List<Review> reviews = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Review review = new Review(
                        resultSet.getInt("id"),
                        resultSet.getInt("course_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getInt("rating"),
                        resultSet.getString("comment"),
                        Timestamp.valueOf(resultSet.getString("timestamp"))
                );
                review.setCourseMnemonic(resultSet.getString("CourseSubject") + " " + resultSet.getInt("CourseNumber"));
                review.setCourseTitle(resultSet.getString("CourseTitle"));
                reviews.add(review);
            }
        } catch (SQLException e) {
            System.err.println("Couldn't fetch reviews for " + userId + " - " + e.getMessage());
            throw e;
        }
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for userId: " + userId);
        }

        return reviews;
    }



}

