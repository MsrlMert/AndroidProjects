package com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mertmsrl.mef_idp_project.Models.User;

import java.util.ArrayList;
import java.util.List;

public class MatchedStudentModelView extends ViewModel {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    MutableLiveData<List<User>> userMutableLiveData;
    final private String USERS_COLLECTION_NAME = "users";
    final private String VOICERS_COLLECTION_NAME = "voicers";
    Query query = firestore.collection(VOICERS_COLLECTION_NAME).document(currentUser.getUid()).collection("MatchedStudent");
    Context context;

    public MatchedStudentModelView() {
    }

    public MatchedStudentModelView(Context context) {
        this.context = context;
    }

    public LiveData<List<User>> getUsers(){
        if (userMutableLiveData == null){
            userMutableLiveData = new MutableLiveData<>();
            loadUsers();
        }
        return userMutableLiveData;
    }

    private void loadUsers() {
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null){
                    List<User> matchedStudentList = new ArrayList<>();
                    for (DocumentSnapshot document : value.getDocuments()){
                        String userId = document.get("userId").toString();
                        String userName = document.get("userName").toString();
                        String userEmail = document.get("userEmail").toString();
                        String courseName = document.get("userCourseName").toString();
                        String userPassword = document.get("userPassword").toString();

                        User user = new User(userId, userName, userEmail, userPassword, courseName);
                        matchedStudentList.add(user);
                    }
                    userMutableLiveData.setValue(matchedStudentList);
                }
            }
        });

    }
}
