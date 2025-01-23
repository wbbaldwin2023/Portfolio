package edu.virginia.sde.reviews;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class UserDatabaseSetup {
    private static final String URL = "jdbc:sqlite:course_reviews.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static boolean userValidation(String username, String password) {

        username = username.trim();
        password = password.trim();

        String query = "SELECT id FROM users WHERE username = ? AND password = ?";
        try (Connection connection = connect();
             PreparedStatement sqlstatement = connection.prepareStatement(query)) {
            sqlstatement.setString(1, username);
            sqlstatement.setString(2, password);

            ResultSet results = sqlstatement.executeQuery();
            if (results.next()) {
                int userId = results.getInt("id");
                System.out.println("userId" + userId);
                User.getInstance().setUser(userId, username);
                System.out.println("Validation succeeded for user: " + username);
                return true;
            } else {
                System.out.println("Validation failed for user: " + username);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean makeNewUser(String username, String password) {

        if (password.length() < 8) {
            System.out.println("Password needs to be at least 8 characters");
            return false;
        }
        String query = "INSERT INTO users (username, password) VALUES (?, ?)";
        return executeUpdate(query, username, password);
    }



    private static boolean executeUpdate(String query, Object... params) {
        try (Connection connection = connect();
             PreparedStatement sqlstatement = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                sqlstatement.setObject(i + 1, params[i]);
            }
            sqlstatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Optional<Integer> getUserId(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        try (Connection connection = connect();
             PreparedStatement sqlstatement = connection.prepareStatement(query)) {
            sqlstatement.setString(1, username);

            ResultSet resultSet = sqlstatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
