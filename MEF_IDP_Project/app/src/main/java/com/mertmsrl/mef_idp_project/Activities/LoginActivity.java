package com.mertmsrl.mef_idp_project.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mertmsrl.mef_idp_project.Activities.UserActivities.MainActivityUser;
import com.mertmsrl.mef_idp_project.Activities.VoicerActivities.MainActivityVoicer;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.HistoryFragment;
import com.mertmsrl.mef_idp_project.R;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    final private String USERS_COLLECTION_NAME = "users";
    final private String VOICERS_COLLECTION_NAME = "voicers";
    EditText editTextUserEmail, editTextUserPassword;
    Button btnLogin, btnGoToRegisterActivity;
    CheckBox checkBoxVoicer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }


    public void init() {
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        checkBoxVoicer = findViewById(R.id.login_activity_checkbox_voicer);
        editTextUserEmail = findViewById(R.id.login_activity_edit_text_user_email);
        editTextUserPassword = findViewById(R.id.login_activity_edit_text_user_password);

        if (firebaseUser != null && firebaseUser == firebaseAuth.getCurrentUser()) {

            firestore.collection(VOICERS_COLLECTION_NAME)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                if (document.get("voicerEmail").equals(firebaseUser.getEmail())) {
                                    goToMainActivityVoicer();
                                    return;
                                }
                            }
                            goToMainActivityUser();
                        }
                    });

        }


        btnLogin = findViewById(R.id.login_activity_btn_login);
        btnGoToRegisterActivity = findViewById(R.id.login_activity_btn_go_to_register_activity);
    }

    public interface CheckIsVoicer{
        void isVoicer(boolean result);
    }


    public void btnLoginListener(View view) {
        if (!TextUtils.isEmpty(editTextUserEmail.getText())) {
            if (!TextUtils.isEmpty(editTextUserPassword.getText())) {
                String userEmail = editTextUserEmail.getText().toString();
                String userPassword = editTextUserPassword.getText().toString();

                firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Go To Main Activity
                                if (task.isSuccessful()) {
                                    isVoicer(userEmail, userPassword, new CheckIsVoicer() {
                                        @Override
                                        public void isVoicer(boolean result) {
                                            if (result){
                                                goToMainActivityVoicer();
                                            }
                                            else
                                                goToMainActivityUser();
                                        }
                                    });

                                } else
                                    Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }

    }

    public void btnGoToRegisterActivityListener(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    public void goToMainActivityUser() {
        Intent intent = new Intent(this, MainActivityUser.class);
        startActivity(intent);
    }

    public void goToMainActivityVoicer() {
        Intent intent = new Intent(this, MainActivityVoicer.class);
        startActivity(intent);
    }

    public void isVoicer(String userEmail, String userPassword, CheckIsVoicer checkIsVoicer){
        
        
        firestore.collection(VOICERS_COLLECTION_NAME)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots){

                            Toast.makeText(LoginActivity.this, document.get("voicerEmail").toString()+" "+userEmail, Toast.LENGTH_SHORT).show();
                            if (document.get("voicerEmail").equals(userEmail) && document.get("voicerPassword").equals(userPassword)){
                                checkIsVoicer.isVoicer(true);
                                Toast.makeText(LoginActivity.this, "voicer", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                        checkIsVoicer.isVoicer(false);
                        Toast.makeText(LoginActivity.this, "Not Voicer", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}