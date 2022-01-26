package com.mertmsrl.mef_idp_project.Models;

public class Voicer {

    private String voicerId, voicerName, voicerEmail, voicerPassword;

    public Voicer(String voicerId, String voicerName, String voicerEmail, String voicerPassword) {
        this.voicerId = voicerId;
        this.voicerName = voicerName;
        this.voicerEmail = voicerEmail;
        this.voicerPassword = voicerPassword;
    }

    public String getVoicerId() {
        return voicerId;
    }

    public void setVoicerId(String voicerId) {
        this.voicerId = voicerId;
    }

    public String getVoicerName() {
        return voicerName;
    }

    public void setVoicerName(String voicerName) {
        this.voicerName = voicerName;
    }

    public String getVoicerEmail() {
        return voicerEmail;
    }

    public void setVoicerEmail(String voicerEmail) {
        this.voicerEmail = voicerEmail;
    }

    public String getVoicerPassword() {
        return voicerPassword;
    }

    public void setVoicerPassword(String voicerPassword) {
        this.voicerPassword = voicerPassword;
    }
}
