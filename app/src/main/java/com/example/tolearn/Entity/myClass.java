package com.example.tolearn.Entity;

public class myClass {

    // class
    int id;
    String category;
    String classroom_token;
    String title;
    String teacher_name;
    String time;
    String avatar;
    String description;
    int limit;
    public myClass(int id,String category, String classroom_token, String avatar, String class_title, String teacher_name, String description, int limit, String time){
        this.id = id;
        this.category = category;
        this.classroom_token = classroom_token;
        this.avatar = avatar;
        this.title = class_title;
        this.teacher_name = teacher_name;
        this.description = description;
        this.limit = limit;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }

    public String getClassroom_token() {
        return classroom_token;
    }

    public String getTeacher_name() {
        return teacher_name;
    }

    public String getTime() {
        return time;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getDescription() {
        return description;
    }

    public int getLimit() {
        return limit;
    }
}
