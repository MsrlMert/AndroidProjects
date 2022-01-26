package com.mertmsrl.intentapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        String realmName = "My Project";
        RealmConfiguration config = new RealmConfiguration.Builder().name(realmName).build();

//        Realm backgroundThreadRealm = Realm.getInstance(config);
        Realm.setDefaultConfiguration(config);


    }

}
