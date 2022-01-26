package com.mertmsrl.againdatabase;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyDataBase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(getApplicationContext());
//        RealmConfiguration configuration = new RealmConfiguration.Builder().name("myDB.realm").build();

        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).name("myDB2.realm").build();
        Realm.setDefaultConfiguration(configuration);
    }
}
