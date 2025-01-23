package edu.virginia.sde.reviews;

import javafx.beans.property.*;

//AI Agent: ChatGPT
//Prompt: can you write the course class for me
public class Course {

    private final IntegerProperty id; // Add an ID property
    private final StringProperty subject;
    private final IntegerProperty number;
    private final StringProperty title;
    private final StringProperty averageReview;

    public Course(int id, String subject, int number, String title, String averageReview) {
        this.id = new SimpleIntegerProperty(id); // Initialize the ID property
        this.subject = new SimpleStringProperty(subject);
        this.number = new SimpleIntegerProperty(number);
        this.title = new SimpleStringProperty(title);
        this.averageReview = new SimpleStringProperty(averageReview);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getSubject() {
        return subject.get();
    }

    public int getNumber() {
        return number.get();
    }

    public String getTitle() {
        return title.get();
    }

    public String getAverageReview() {
        return averageReview.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setSubject(String subject) {
        this.subject.set(subject);
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public void setAverageReview(String averageReview) {
        this.averageReview.set(averageReview);
    }

    // Properties
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty subjectProperty() {
        return subject;
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty averageReviewProperty() {
        return averageReview;
    }

    @Override
    public String toString() {
        return String.format(
                "Course{id=%d, subject='%s', number=%d, title='%s', averageReview='%s'}",
                getId(), getSubject(), getNumber(), getTitle(), getAverageReview()
        );
    }
}

