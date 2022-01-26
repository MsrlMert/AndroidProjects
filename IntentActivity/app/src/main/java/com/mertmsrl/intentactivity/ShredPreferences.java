package com.mertmsrl.intentactivity;

import android.content.Context;
import android.content.SharedPreferences;

public class ShredPreferences {

    final String PREFERENCES_NAME = "file";
    public void save(Context context, String key, String value){

        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValue(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        String text = preferences.getString(key,null);

        return text;
    }

    public void remove(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear(Context context, String key){
        SharedPreferences preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }


}
