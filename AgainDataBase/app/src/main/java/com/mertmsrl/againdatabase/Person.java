package com.mertmsrl.againdatabase;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class Person extends RealmObject {

    private String personUserName;
    private String personPassword;
    private String personName;
    private String personGender;


    public Person() {

    }

    public Person(String personUserName, String personPassword, String personGender, String personName) {
        this.personUserName = personUserName;
        this.personPassword = personPassword;
        this.personGender = personGender;
        this.personName = personName;
    }

    public String getPersonUserName() {
        return personUserName;
    }

    public void setPersonUserName(String personUserName) {
        this.personUserName = personUserName;
    }

    public String getPersonPassword() {
        return personPassword;
    }

    public void setPersonPassword(String personPassword) {
        this.personPassword = personPassword;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personUserName='" + personUserName + '\'' +
                ", personPassword='" + personPassword + '\'' +
                ", personName='" + personName + '\'' +
                ", personGender='" + personGender + '\'' +
                '}';
    }
}
