package com.mertmsrl.chatapplication.Activities;

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
import com.google.android.material.textfield.TextInputLayout;
import com.google.api.Authentication;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertmsrl.chatapplication.Classes.Users;
import com.mertmsrl.chatapplication.R;

public class RegisterActivity extends AppCompatActivity {

    // FireBase
    FirebaseFirestore firestore;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    // Register Activity Layout Views
    TextInputLayout textInputLayoutUserName, textInputLayoutUserEmail, textInputLayoutUserPassword;
    Button btnSignIn;
    EditText editTextUserName, editTextUserEmail, editTextUserPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    public void init(){
        textInputLayoutUserName = findViewById(R.id.register_activity_text_layout_user_name);
        textInputLayoutUserEmail = findViewById(R.id.register_activity_text_layout_user_email);
        textInputLayoutUserPassword = findViewById(R.id.register_activity_text_layout_user_password);

        editTextUserName = findViewById(R.id.register_activity_edit_text_user_name);
        editTextUserEmail = findViewById(R.id.register_activity_edit_text_user_email);
        editTextUserPassword = findViewById(R.id.register_activity_edit_text_user_password);

        btnSignIn = findViewById(R.id.activity_register_btn_sign_in);

        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void activity_register_btn_sign_inOnClick(View view) {

        String userName = editTextUserName.getText().toString();
        String userEmail = editTextUserEmail.getText().toString();
        String userPassword = editTextUserPassword.getText().toString();


        if (!TextUtils.isEmpty(userName)){
            if (!TextUtils.isEmpty(userEmail)){
                if (!TextUtils.isEmpty(userPassword)){

                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){
                                        String userId = FirebaseAuth.getInstance().getUid();
                                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                        Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                        Users user = new Users(userId, userName, userEmail, userPassword, "default");
                                        createUser(user);
                                        Toast.makeText(RegisterActivity.this, user.getUserId(), Toast.LENGTH_SHORT).show();
                                        finish();
                                        startActivity(intent);
                                    }else
                                        Toast.makeText(RegisterActivity.this, "Not Successful "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }else
                    textInputLayoutUserPassword.setError("Password Can Not Be Empty");
            }else
                textInputLayoutUserEmail.setError("Email Can Not Be Empty");
        }else
            textInputLayoutUserName.setError("Name Can not Be Empty");
    }

    public void createUser(Users user){
        firestore.collection("users").document(user.getUserId())
                .set(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);

        startActivity(intent);

    }
}