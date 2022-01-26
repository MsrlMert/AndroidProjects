package com.mertmsrl.mef_idp_project.Activities.VoicerActivities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.HistoryFragment;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.RecordFragment;
import com.mertmsrl.mef_idp_project.R;

public class RecordActivity extends AppCompatActivity {
    String userId, userName, userEmail;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    FirebaseFirestore firestore;
    FirebaseStorage firebaseStorage;
    ImageView imgViewRecordHistory;

    public RecordActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();

    }

    public void init() {

        Bundle bundle = getIntent().getExtras();
        userId = bundle.getString("userId");
        userName = bundle.getString("userName");
        userEmail = bundle.getString("userEmail");

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        RecordFragment recordFragment = new RecordFragment(fragmentManager, this, userId, userName
                , userEmail);
        fragmentTransaction.replace(R.id.activity_record_frame_layout, recordFragment);
        fragmentTransaction.commit();
    }



}