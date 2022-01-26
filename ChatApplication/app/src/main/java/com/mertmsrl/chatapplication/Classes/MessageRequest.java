package com.mertmsrl.chatapplication.Classes;

public class MessageRequest {
    private String targetUserId, currentUserId;

    public MessageRequest() {
    }

    public MessageRequest(String targetUserId, String currentUserId) {
        this.targetUserId = targetUserId;
        this.currentUserId = currentUserId;

    }



    public String getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(String targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                ", targetUserId='" + targetUserId + '\'' +
                ", currentUserId='" + currentUserId + '\'' +
                '}';
    }
}
