package edu.virginia.sde.reviews;
import java.sql.Timestamp;

public class Review {
    private int id;
    private int courseId;
    private int rating;
    private String comment;
    private Timestamp timestamp;
    private int userId;
    private String courseMnemonic;
    private String courseTitle;

    public Review(int id, int courseId, int userId, int rating, String comment, Timestamp timestamp) {
        this.id = id;
        this.userId = userId;
        this.courseId = courseId;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getUserId() {
        return userId;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getCourseMnemonic() {
        return courseMnemonic;
    }

    public void setCourseMnemonic(String courseMnemonic) {
        this.courseMnemonic = courseMnemonic;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }
}
