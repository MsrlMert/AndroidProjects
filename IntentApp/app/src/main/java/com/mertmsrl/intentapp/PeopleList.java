package com.mertmsrl.intentapp;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class PeopleList extends RealmObject {
    private String personName, personSurname;
    private int personIncome;
    private int personAge;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonSurname() {
        return personSurname;
    }

    public void setPersonSurname(String personSurname) {
        this.personSurname = personSurname;
    }

    public int getPersonIncome() {
        return personIncome;
    }

    public void setPersonIncome(int personIncome) {
        this.personIncome = personIncome;
    }

    public int getPersonAge() {
        return personAge;
    }

    public void setPersonAge(int personAge) {
        this.personAge = personAge;
    }

    @Override
    public String toString() {
        return "PeopleList{" +
                "personName='" + personName + '\'' +
                ", personSurname='" + personSurname + '\'' +
                ", personIncome=" + personIncome +
                ", personAge=" + personAge +
                '}';
    }
}

