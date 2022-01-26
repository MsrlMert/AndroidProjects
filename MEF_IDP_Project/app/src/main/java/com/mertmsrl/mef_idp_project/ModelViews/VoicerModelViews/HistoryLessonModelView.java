package com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews;

import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class HistoryLessonModelView extends ViewModel {

    MutableLiveData<List<String>> mutableLiveDataLessons;
    List<String> stringLessonList;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    Query query ;
    String userId;




    public LiveData<List<String>> getLessons(String userId){
        this.userId = userId;
        if (mutableLiveDataLessons == null){
            mutableLiveDataLessons = new MutableLiveData<>();
            loadLessons();
        }
        return mutableLiveDataLessons;
    }

    private void loadLessons() {
        query = firestore.collection("users").document(userId).collection("lessons").document("türkçe")
        .collection("records");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                Log.i("isEmpty", String.valueOf(value.isEmpty()));
                stringLessonList = new ArrayList<>();
                Log.i("val", String.valueOf(value.size()));
                if (value != null){
                    for (DocumentSnapshot document : value.getDocuments()){
                        Log.d("doc", document.getId());
                        stringLessonList.add(document.getId());
                    }
                    mutableLiveDataLessons.setValue(stringLessonList);
                }
            }
        });
    }

}
