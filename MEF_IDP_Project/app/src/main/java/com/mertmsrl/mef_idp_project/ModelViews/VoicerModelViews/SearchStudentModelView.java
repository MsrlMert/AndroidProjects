package com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mertmsrl.mef_idp_project.Models.User;

import java.util.ArrayList;
import java.util.List;

public class SearchStudentModelView extends ViewModel {
    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    MutableLiveData<List<User>> userMutableLiveData;
    final private String USERS_COLLECTION_NAME = "users";
    Query query = firestore.collection(USERS_COLLECTION_NAME);
    Context context;

    public SearchStudentModelView() {
    }

    public SearchStudentModelView(Context context) {
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
                    List<User> userList = new ArrayList<>();
                    for (DocumentSnapshot document : value.getDocuments()){
                        User user = document.toObject(User.class);
                        userList.add(user);
                    }
                    userMutableLiveData.setValue(userList);
                }
            }
        });
    }
}
