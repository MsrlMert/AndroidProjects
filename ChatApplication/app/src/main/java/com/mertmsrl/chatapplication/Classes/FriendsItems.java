package com.mertmsrl.chatapplication.Classes;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

public class FriendsItems {
    private String friendUserId, friendName;



    public FriendsItems() {
    }

    public FriendsItems(String friendUserId, String friendName) {
        this.friendName = friendName;
        this.friendUserId = friendUserId;
    }

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }

    public String getFriendName() {
        return friendName;

    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

}
