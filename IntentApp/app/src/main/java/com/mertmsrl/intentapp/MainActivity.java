package com.mertmsrl.intentapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

//    Button btnSubmit;
//    EditText editTextEmail, editTextSubject,  editTextText;

    EditText editTextUserName, editTextMail, editTextPassword;
    Button btnOpenAlert, btnSignIn, btnExit;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        define();
//        submitMail();
//        defineBtn();
        defineRealm();
        addTable();
        showPeopleData();
    }


    public void defineRealm(){
        realm = Realm.getDefaultInstance();
    }

    public void addTable(){
        realm.beginTransaction();
        PeopleList peopleList = realm.createObject(PeopleList.class);
        peopleList.setPersonName("fatih");
        peopleList.setPersonSurname("genc");
        peopleList.setPersonAge(15);
        peopleList.setPersonIncome(4500);
        realm.commitTransaction();
    }

    public void showPeopleData(){
        realm.beginTransaction();
        RealmResults<PeopleList> result = realm.where(PeopleList.class).findAll();

        for (PeopleList people : result){
            Log.i("result",people.toString());
        }
        realm.commitTransaction();
    }

//    public void define(){
//        btnSubmit = findViewById(R.id.btnSubmit);
//        editTextEmail = findViewById(R.id.editTextEmail);
//        editTextSubject = findViewById(R.id.editTextSubject);
//        editTextText = findViewById(R.id.editTextText);
//    }

//    public void defineBtn(){
//        btnOpenAlert = findViewById(R.id.btnOpenAlert);
//        btnOpenAlert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                alertDialog();
//            }
//        });
//    }

//    public void alertDialog() {
//        LayoutInflater inflater = this.getLayoutInflater();
//        View viewInflate = inflater.inflate(R.layout.alertlayout, null);
//
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setView(viewInflate);
//        alert.setCancelable(false);
//        AlertDialog dialog = alert.create();
//
//        btnSignIn = viewInflate.findViewById(R.id.btnSignIn);
//        btnExit = viewInflate.findViewById(R.id.btnExit);
//
//        editTextUserName = viewInflate.findViewById(R.id.editTextUser);
//        editTextMail = viewInflate.findViewById(R.id.editTextUserMail);
//        editTextPassword = viewInflate.findViewById(R.id.editTextPassword);
//


//        btnSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(getApplicationContext(),editTextMail.getText().toString()+" "+editTextUserName.getText().toString()+" "+
//                        editTextPassword.getText().toString(), Toast.LENGTH_LONG).show();
//
//                dialog.cancel();
//            }
//        });
//        dialog.show();
//
//        btnExit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.cancel();
//            }
//        });
//
//    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//
//    }
    //    public void submitMail(){
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String mailAddress = editTextEmail.getText().toString();
//                String mailSubject = editTextSubject.getText().toString();
//                String mailText = editTextText.getText().toString();
//
//
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("message");
//                intent.putExtra(Intent.EXTRA_EMAIL, mailAddress);
//                intent.putExtra(Intent.EXTRA_SUBJECT, mailSubject);
//                intent.putExtra(Intent.EXTRA_TEXT, mailText);
//
//                startActivity(intent);
//
//            }
//        });
//    }


}