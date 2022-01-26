package com.mertmsrl.gpsapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertmsrl.gpsapplication.Activities.Models.Users;
import com.mertmsrl.gpsapplication.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister;
    EditText editTextEmail, editTextPassword;
    FirebaseFirestore firestore;
    FirebaseUser currentUser;
    final String USER_COLLECTION_NAME = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();
        btnRegisterListener();
    }

    public void init() {
        firestore = FirebaseFirestore.getInstance();


        btnRegister = findViewById(R.id.activity_register_btn_register);

        editTextEmail = findViewById(R.id.activity_register_edit_text_email);
        editTextPassword = findViewById(R.id.activity_register_edit_text_password);

    }

    public void btnRegisterListener() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = editTextEmail.getText().toString();
                String userPassword = editTextPassword.getText().toString();
                if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword)) {
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        currentUser = FirebaseAuth.getInstance().getCurrentUser();
                                        Users user = new Users(currentUser.getUid(), userEmail, userPassword);
                                        firestore.collection(USER_COLLECTION_NAME).document(currentUser.getUid())
                                                .set(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()){
                                                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                        }
                                                        else {
                                                            Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    } else {
                                        Toast.makeText(RegisterActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}