package com.example.tolearn.Entity;

public class Homework {

    // homework entity
    int id;
    String homework_token;
    String title;
    String file;
    String description;
    String deadline;
    int classroom;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHomework_token() {
        return homework_token;
    }

    public void setHomework_token(String homework_token) {
        this.homework_token = homework_token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getClassroom() {
        return classroom;
    }

    public void setClassroom(int classroom) {
        this.classroom = classroom;
    }

    public Homework(int id, String homework_token, String title, String file, String description, String deadline, int classroom) {
        this.id = id;
        this.homework_token = homework_token;
        this.title = title;
        this.file = file;
        this.description = description;
        this.deadline = deadline;
        this.classroom = classroom;
    }
}
