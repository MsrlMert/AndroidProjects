package com.mertmsrl.mef_idp_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertmsrl.mef_idp_project.Activities.UserActivities.MainActivityUser;
import com.mertmsrl.mef_idp_project.Activities.VoicerActivities.MainActivityVoicer;
import com.mertmsrl.mef_idp_project.Models.User;
import com.mertmsrl.mef_idp_project.Models.Voicer;
import com.mertmsrl.mef_idp_project.R;

import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    Button btnRegister;
    EditText editTextUserName, editTextUserEmail, editTextUserPassword;
    CheckBox checkBox;
    final private String USERS_COLLECTION_NAME = "users";
    final private String VOICERS_COLLECTION_NAME = "voicers";
    private ArrayList<String> courseArrayList = new ArrayList<>();
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        setAdapterData();
        btnRegisterListener();

    }

    public void init() {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        courseArrayList.add("Üniversite");
        courseArrayList.add("Kpss");
        courseArrayList.add("Yabancı Dil");

        editTextUserName = findViewById(R.id.register_activity_edit_text_user_name);
        editTextUserEmail = findViewById(R.id.register_activity_edit_text_user_email);
        editTextUserPassword = findViewById(R.id.register_activity_edit_text_user_password);
        btnRegister = findViewById(R.id.register_activity_btn_register);
        spinner = findViewById(R.id.register_activity_spinner);
        checkBox = findViewById(R.id.register_activity_check_box);

    }


    public void btnRegisterListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextUserName.getText())) {
                    if (!TextUtils.isEmpty(editTextUserEmail.getText())) {
                        if (!TextUtils.isEmpty(editTextUserPassword.getText())) {
                            if (!checkBox.isChecked()) {
                                String userName = editTextUserName.getText().toString();
                                String userEmail = editTextUserEmail.getText().toString();
                                String userPassword = editTextUserPassword.getText().toString();
                                String courseName = spinner.getSelectedItem().toString();

                                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    firebaseUser = firebaseAuth.getCurrentUser();
                                                    User user = new User(firebaseUser.getUid(), userName, userEmail, userPassword, courseName);
                                                    firestore.collection(USERS_COLLECTION_NAME).document(firebaseUser.getUid())
                                                            .set(user)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    // Go To Main Activity
                                                                    if (task.isSuccessful()) {
                                                                        goToMainActivityUser();
                                                                    } else
                                                                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                } else {
                                                    Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }

                            else if (checkBox.isChecked()){
                                String userName = editTextUserName.getText().toString();
                                String userEmail = editTextUserEmail.getText().toString();
                                String userPassword = editTextUserPassword.getText().toString();

                                firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()){
                                                    firebaseUser = firebaseAuth.getCurrentUser();
                                                    Voicer voicer = new Voicer(firebaseUser.getUid(), userName, userEmail, userPassword);

                                                    firestore.collection(VOICERS_COLLECTION_NAME).document(firebaseUser.getUid())
                                                            .set(voicer)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()){
                                                                        goToMainActivityVoicer();
                                                                    }else
                                                                        Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                }else
                                                    Toast.makeText(RegisterActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            }
                        }
                    }
                }
            }
        });

    }

    public void goToMainActivityUser() {
        Intent intent = new Intent(this, MainActivityUser.class);

//        Bundle bundle = new Bundle();
//        bundle.putString("userName", userName);
//        bundle.putString("courseName", courseName);
//
//        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void goToMainActivityVoicer() {
        Intent intent = new Intent(this, MainActivityVoicer.class);

//        Bundle bundle = new Bundle();
//        bundle.putString("userName", userName);
//        bundle.putString("courseName", courseName);
//
//        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void setAdapterData() {
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, courseArrayList);
        spinner.setAdapter(stringArrayAdapter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }
}