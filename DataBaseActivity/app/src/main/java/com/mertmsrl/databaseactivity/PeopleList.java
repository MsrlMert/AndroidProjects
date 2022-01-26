package com.mertmsrl.databaseactivity;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

@RealmClass
public class PeopleList extends RealmObject {

    private String userName;
    private String password;
    private String gender;
    private String personName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    @Override
    public String toString() {
        return "PeopleList{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", personName='" + personName + '\'' +
                '}';
    }
}
