package com.mertmsrl.databaseactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Realm realm;
    EditText editTextUserName, editTextPassword, editTextPersonName;
    RadioButton radioButtonMan, radioButtonWoman;
    Button btnSignIn;
    RadioGroup radioGroupGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        define();
        addPerson();
        showPersonData();

    }

    public void define(){
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextPersonName = findViewById(R.id.editTextPersonName);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioButtonMan = findViewById(R.id.radioMan);
        radioButtonWoman = findViewById(R.id.radioWoman);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    public void addPerson(){
//        int genderId = radioGroupGender.getCheckedRadioButtonId();
//        RadioButton selectedGender = findViewById(genderId);
//        String personGender = selectedGender.getText().toString();
//        final String[] personGender = new String[1];
//        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                personGender[0] = findViewById(checkedId).toString();
//            }
//        });
        final String personGender = "Man";

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String personName = editTextPersonName.getText().toString();
                final String personUserName = editTextUserName.getText().toString();
                final String personPassword = editTextPassword.getText().toString();


                addToDB(personUserName, personPassword, personName, personGender);
            }
        });
    }

    public void addToDB(final String userName, final String password, final String personName,final String personGender){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                PeopleList peopleList = realm.createObject(PeopleList.class);
                peopleList.setUserName(userName);
                peopleList.setPassword(password);
                peopleList.setPersonName(personName);
                peopleList.setGender(personGender);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), userName+" "+password+" "+personName,Toast.LENGTH_LONG).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), "unsuccessful", Toast.LENGTH_LONG).show();

            }
        });
    }

    public void showPersonData(){

        RealmResults<PeopleList> peopleList = realm.where(PeopleList.class).findAll();

        for (PeopleList people : peopleList){
            Log.i("person ", people.toString());
        }
    }





}