package com.mertmsrl.mef_idp_project.Models;

import com.google.firebase.Timestamp;

public class RecordHistoryModel {
    String audioName, userName, lessonName;
    Timestamp audioTime;

    public RecordHistoryModel(String audioName, String userName, String lessonName, Timestamp audioTime) {
        this.audioName = audioName;
        this.userName = userName;
        this.lessonName = lessonName;
        this.audioTime = audioTime;
    }

    public String getAudioName() {
        return audioName;
    }

    public void setAudioName(String audioName) {
        this.audioName = audioName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public Timestamp getAudioTime() {
        return audioTime;
    }

    public void setAudioTime(Timestamp audioTime) {
        this.audioTime = audioTime;
    }
}
