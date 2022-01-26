package com.mertmsrl.chatapplication.Classes;

import com.google.firebase.Timestamp;

public class Messages {
    private String messageDocId, receiverId, senderId, messageContent, messageType;
    private Timestamp messageTime;
    private boolean messageIsRead;



    public Messages() {
    }

    public Messages(String messageType, String messageDocId, String receiverId, String senderId, String messageContent, Timestamp messageTime, boolean messageIsRead) {
        this.messageDocId = messageDocId;
        this.receiverId = receiverId;
        this.senderId = senderId;
        this.messageContent = messageContent;
        this.messageType = messageType;
        this.messageTime = messageTime;
        this.messageIsRead = messageIsRead;

    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageDocId() {
        return messageDocId;
    }

    public void setMessageDocId(String messageDocId) {
        this.messageDocId = messageDocId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public Timestamp getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(Timestamp messageTime) {
        this.messageTime = messageTime;
    }


    public boolean isMessageIsRead() {
        return messageIsRead;
    }

    public void setMessageIsRead(boolean isMessageRead) {
        this.messageIsRead = isMessageRead;
    }

    @Override
    public String toString() {
        return "Messages{" +
                "messageRandomCode='" + messageDocId + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", senderId='" + senderId + '\'' +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }
}
