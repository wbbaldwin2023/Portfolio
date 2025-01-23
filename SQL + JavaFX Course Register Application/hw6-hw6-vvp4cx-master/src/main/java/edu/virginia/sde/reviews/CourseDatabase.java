package edu.virginia.sde.reviews;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDatabase {

    private static final String dbUrl = "jdbc:sqlite:course_reviews.db";


    public static void initializeDatabase() {

        //AI Agent: ChatGPT
        //Prompt: can you write the sql query to create the courses data base table
        String createCoursesTable = """
            CREATE TABLE IF NOT EXISTS Courses (
                CourseId INTEGER PRIMARY KEY AUTOINCREMENT,
                CourseSubject TEXT NOT NULL CHECK (LENGTH(CourseSubject) BETWEEN 2 AND 4),
                CourseNumber INTEGER NOT NULL CHECK (CourseNumber > 999 AND CourseNumber < 10000),
                CourseTitle TEXT NOT NULL CHECK (LENGTH(CourseTitle) BETWEEN 1 AND 50),
                AvgReview REAL,
                UNIQUE (CourseSubject, CourseNumber, CourseTitle)
            );
        """;

        try (Connection dbConnection = connectToDatabase();
             Statement dbStatement = dbConnection.createStatement()) {
            dbStatement.execute(createCoursesTable);
        } catch (SQLException dbError) {
            System.err.println("Error loading the course database" + dbError.getMessage());
        }
    }

    private static Connection connectToDatabase() throws SQLException {
        return DriverManager.getConnection(dbUrl);
    }


    public static List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();
        String fetchCoursesQuery = "SELECT * FROM Courses";

        try (Connection dbConnection = connectToDatabase();
             Statement dbStatement = dbConnection.createStatement();
             ResultSet coursesResultSet = dbStatement.executeQuery(fetchCoursesQuery)) {

            while (coursesResultSet.next()) {
                int courseId = coursesResultSet.getInt("CourseId");
                String courseSubject = coursesResultSet.getString("CourseSubject");
                int courseNumber = coursesResultSet.getInt("CourseNumber");
                String courseTitle = coursesResultSet.getString("CourseTitle");
                Double avgReview = coursesResultSet.getDouble("AvgReview");

                String avgReviewString = "";
                if (avgReview != 0.0) {
                    avgReviewString = String.format("%.2f", avgReview);
                }
                allCourses.add(new Course(courseId, courseSubject, courseNumber, courseTitle, avgReviewString));
            }
        } catch (SQLException dbError) {
            System.err.println("Error could not fetch courses from db " + dbError.getMessage());
        }

        return allCourses;
    }

    //AI Agent: ChatGPT
    //Prompt: Write code for the searchCourses method
    public static List<Course> searchCourses(String courseSubject, String courseNumber, String courseTitle) {
        List<Course> matchingCourses = new ArrayList<>();
        String searchCoursesQuery = """
            SELECT * FROM Courses
            WHERE (LOWER(CourseSubject) LIKE ? OR ? IS NULL)
              AND (CAST(CourseNumber AS TEXT) LIKE ? OR ? IS NULL)
              AND (LOWER(CourseTitle) LIKE ? OR ? IS NULL);
        """;

        try (Connection dbConnection = connectToDatabase();
             PreparedStatement dbPreparedStatement = dbConnection.prepareStatement(searchCoursesQuery)) {
            dbPreparedStatement.setString(1, courseSubject == null || courseSubject.isBlank() ? null : "%" + courseSubject.toLowerCase() + "%");
            dbPreparedStatement.setString(2, courseSubject);
            dbPreparedStatement.setString(3, courseNumber == null || courseNumber.isBlank() ? null : "%" + courseNumber + "%");
            dbPreparedStatement.setString(4, courseNumber);
            dbPreparedStatement.setString(5, courseTitle == null || courseTitle.isBlank() ? null : "%" + courseTitle.toLowerCase() + "%");
            dbPreparedStatement.setString(6, courseTitle);

            ResultSet coursesResultSet = dbPreparedStatement.executeQuery();
            while (coursesResultSet.next()) {
                int courseId = coursesResultSet.getInt("CourseId");
                String fetchedSubject = coursesResultSet.getString("CourseSubject");
                int fetchedNumber = coursesResultSet.getInt("CourseNumber");
                String fetchedTitle = coursesResultSet.getString("CourseTitle");
                Double avgReview = coursesResultSet.getDouble("AvgReview");

                matchingCourses.add(new Course(courseId, fetchedSubject, fetchedNumber, fetchedTitle,
                        avgReview != 0.0 ? String.format("%.2f", avgReview) : ""));
            }
        } catch (SQLException dbError) {
            System.err.println("Error searching courses: " + dbError.getMessage());
        }

        return matchingCourses;
    }


    public static void addCourse(String courseSubject, int courseNumber, String courseTitle, Double avgReview) throws SQLException{

        String checkCourseQuery = """
            SELECT COUNT(*) FROM Courses
            WHERE CourseSubject = ? AND CourseNumber = ? AND CourseTitle = ?;
        """;


        String insertCourseQuery = """
            INSERT INTO Courses (CourseSubject, CourseNumber, CourseTitle, AvgReview)
            VALUES (?, ?, ?, ?);
        """;


        try (Connection dbConnection = connectToDatabase()) {
            // Check if the course already exists
            try (PreparedStatement checkStatement = dbConnection.prepareStatement(checkCourseQuery)) {
                checkStatement.setString(1, courseSubject);
                checkStatement.setInt(2, courseNumber);
                checkStatement.setString(3, courseTitle);

                ResultSet resultSet = checkStatement.executeQuery();
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    throw new SQLException("Duplicate course");
                }
            }

            try (PreparedStatement insertStatement = dbConnection.prepareStatement(insertCourseQuery)) {
                insertStatement.setString(1, courseSubject.toUpperCase());
                insertStatement.setInt(2, courseNumber);
                insertStatement.setString(3, courseTitle);
                insertStatement.setObject(4, avgReview);
                insertStatement.executeUpdate();
            }
        }
    }

    public static void updateCourseAverageReviewInDatabase(int courseId, String averageReview) {
        String updateQuery = "UPDATE Courses SET AvgReview = ? WHERE CourseId = ?";

        try (Connection dbConnection = connectToDatabase();
             PreparedStatement statement = dbConnection.prepareStatement(updateQuery)) {
            statement.setObject(1, averageReview.isEmpty() ? null : Double.parseDouble(averageReview));
            statement.setInt(2, courseId);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating course average review in database: " + e.getMessage());
        }
    }


    public static Course getCourseById(int courseId) throws SQLException {
        String query = """
            SELECT CourseId, CourseSubject, CourseNumber, CourseTitle, AvgReview
            FROM Courses
            WHERE CourseId = ?
        """;

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:course_reviews.db");
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Course(
                        resultSet.getInt("CourseId"),
                        resultSet.getString("CourseSubject"),
                        resultSet.getInt("CourseNumber"),
                        resultSet.getString("CourseTitle"),
                        resultSet.getString("AvgReview") // This can be null, so handle accordingly
                );
            } else {
                throw new SQLException("No course found with ID: " + courseId);
            }
        }
    }

}


