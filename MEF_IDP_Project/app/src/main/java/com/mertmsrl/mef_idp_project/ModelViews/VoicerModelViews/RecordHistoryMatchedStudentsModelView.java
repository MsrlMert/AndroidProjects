package com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews;

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
import com.mertmsrl.mef_idp_project.Models.RecordHistoryModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecordHistoryMatchedStudentsModelView extends ViewModel {
    MutableLiveData<HashMap<String, String>> mutableLiveDataMatchedStudents;
    MutableLiveData<List<RecordHistoryModel>> mutableLiveDataRecords;

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    Query query;


//    List<String> matchedStudentList = new ArrayList<>();
    HashMap<String, String> matchedStudentHashMap = new HashMap<>();

    final String USER_COLLECTION_NAME = "users";


    public RecordHistoryMatchedStudentsModelView() {
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


    }


    public LiveData<HashMap<String, String>> getRecordHistory() {
        if (mutableLiveDataMatchedStudents == null) {
            mutableLiveDataMatchedStudents = new MutableLiveData<HashMap<String, String>>();
            findMatches();
        }
        return mutableLiveDataMatchedStudents;
    }

    private void findMatches() {
        query = firestore.collection("voicers").document(firebaseUser.getUid())
                .collection("MatchedStudent");

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    matchedStudentHashMap = new HashMap<>();

                    for (DocumentSnapshot document : value.getDocuments()){
                        String userId = document.get("userId").toString();
                        String userEmail = document.get("userEmail").toString();

                        matchedStudentHashMap.put(userId, userEmail);
                    }
                    mutableLiveDataMatchedStudents.setValue(matchedStudentHashMap);
//                    matchedStudentList.clear();
//
//                    for (DocumentSnapshot document : value.getDocuments()) {
//                        matchedStudentList.add(document.get("userEmail").toString());
//                    }
//                    mutableLiveDataMatchedStudents.setValue(matchedStudentList);
                }
            }
        });
    }
}
