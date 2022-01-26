package com.mertmsrl.againdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Trace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmResults;
import okhttp3.internal.http.RealResponseBody;

public class MainActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword, editTextPersonName;
    RadioGroup radioGroupGender;
    RadioButton radioBtnMan, radioBtnWoman;
    Button btnSignIn, btnUpdate;
    Realm realm;
    ListView listView;
    Adapter adapter;
    static int positionStatic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        define();
        addPerson();
        printPerson();
        updatePerson(positionStatic, radioGroupGender);
        listViewListener();


    }


    public void define(){
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextPassword = findViewById(R.id.editTextUserPassword);
        editTextPersonName = findViewById(R.id.editTextPersonName);

        radioGroupGender = findViewById(R.id.radioGroupGender);
        radioBtnMan = findViewById(R.id.radioBtnMan);
        radioBtnWoman = findViewById(R.id.radioBtnWoman);

        btnSignIn = findViewById(R.id.btnSignIn);
        btnUpdate = findViewById(R.id.btnUpdate);

        listView = findViewById(R.id.listView);

    }

    public void addPerson(){


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String personUserName = editTextUserName.getText().toString();
                String personPassword = editTextPassword.getText().toString();
                String personName = editTextPersonName.getText().toString();
                String personGender = findGender(radioGroupGender);

                addToDb(personUserName, personPassword, personName, personGender);
                editTextUserName.setText("");
                editTextPassword.setText("");
                editTextPersonName.setText("");
                radioBtnMan.setChecked(false);
                radioBtnWoman.setChecked(false);

            }
        });
    }






    public void addToDb(String personUserName, String personPassword, String personName, String personGender){

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                Person person = realm.createObject(Person.class);
                person.setPersonUserName(personUserName);
                person.setPersonPassword(personPassword);
                person.setPersonName(personName);
                person.setPersonGender(personGender);


            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {

                Toast.makeText(getApplicationContext(), personUserName+" "+personPassword+" "+personGender,Toast.LENGTH_LONG).show();
                printPerson();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(getApplicationContext(), "Unsuccessful",Toast.LENGTH_LONG);
            }
        });
    }

    public void printPerson(){
        RealmResults<Person> person = realm.where(Person.class).findAll();

        if (person.size() > 0){
            adapter = new Adapter(person,getApplicationContext(),realm);
            listView.setAdapter(adapter);

        }
    }

    public void listViewListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                positionStatic = position;
                openAlert(position);
                printPerson();

            }
        });
    }

    public void deletePerson(final int position){

        RealmResults<Person> personResult = realm.where(Person.class).findAll();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                Person person = personResult.get(position);
                person.deleteFromRealm();
                adapter.notifyDataSetChanged();

            }
        });
    }

    public void updatePerson(final int position, RadioGroup radioGroupGender){
        RealmResults<Person> personRealmResults = realm.where(Person.class).findAll();

        Person person = personRealmResults.get(position);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = takePersonData(radioGroupGender);
                Person personChange = personRealmResults.get(position);

                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        personChange.setPersonUserName(person.getPersonUserName());
                        personChange.setPersonPassword(person.getPersonPassword());
                        personChange.setPersonGender(person.getPersonGender());
                        personChange.setPersonName(person.getPersonName());
                        adapter.notifyDataSetChanged();
                    }
                });

            }
        });

        printPerson();
    }

    public Person takePersonData(RadioGroup radioGroupGender){
        String personUserName = editTextUserName.getText().toString();
        String personPassword = editTextPassword.getText().toString();
        String personGender = findGender(radioGroupGender);
        String personName = editTextPersonName.getText().toString();

        Person person = new Person(personUserName, personPassword, personGender, personName);

        return person;

    }

    public void openAlert(int position){
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alertlayout,null);
        RealmResults<Person> realmResults = realm.where(Person.class).findAll();


        Button btnAlertYes = view.findViewById(R.id.btnAlertYes);
        Button btnAlertNo = view.findViewById(R.id.btnAlertNo);
        Button btnAlertUpdate = view.findViewById(R.id.btnAlertUpdate);

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setView(view);
        alert.setCancelable(false);

        AlertDialog dialog = alert.create();


        btnAlertYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePerson(position);
                dialog.cancel();
            }
        });

        btnAlertNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });
        btnAlertUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Person person = realmResults.get(position);
                editTextUserName.setText(person.getPersonUserName());
                editTextPassword.setText(person.getPersonPassword());

                if (person.getPersonGender().equals("Man")){
                    radioBtnMan.setChecked(true);
                }
                else if (person.getPersonGender().equals("Woman")){
                    radioBtnWoman.setChecked(true);
                }

                editTextPersonName.setText(person.getPersonName());
                dialog.cancel();

            }
        });

        dialog.show();

    }




    public String findGender(RadioGroup radioGroup){
        int checkedBtnId = radioGroup.getCheckedRadioButtonId();
        RadioButton personGenderBtn = findViewById(checkedBtnId);
        String personGender = personGenderBtn.getText().toString();
        return personGender;
    }


}