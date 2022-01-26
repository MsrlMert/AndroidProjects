package com.mertmsrl.mef_idp_project.Models;

public class Book {
    private String bookPublisher, bookCourseName, voiceName, courseYear;


    public Book(String bookPublisher, String bookCourseName, String voiceName, String courseYear) {
        this.bookPublisher = bookPublisher;
        this.bookCourseName = bookCourseName;
        this.voiceName = voiceName;
        this.courseYear = courseYear;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public String getBookCourseName() {
        return bookCourseName;
    }

    public void setBookCourseName(String bookCourseName) {
        this.bookCourseName = bookCourseName;
    }

    public String getVoiceName() {
        return voiceName;
    }

    public void setVoiceName(String voiceName) {
        this.voiceName = voiceName;
    }

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }
}
