package com.mertmsrl.chatapplication.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import com.mertmsrl.chatapplication.R;

public class LoginActivity extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    EditText editTextUserEmail, editTextUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            startMainActivity();
        }
    }

    public void init(){
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        editTextUserEmail = findViewById(R.id.login_activity_edit_text_user_email);
        editTextUserPassword = findViewById(R.id.login_activity_edit_text_user_password);
    }

    public void login_activity_btn_loginOnClick(View view) {
        String userEmail = editTextUserEmail.getText().toString();
        String userPassword = editTextUserPassword.getText().toString();

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startMainActivity();
                        }else
                            Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void login_activity_btn_registerOnClick(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    public void startMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        finish();
        startActivity(intent);
    }
}