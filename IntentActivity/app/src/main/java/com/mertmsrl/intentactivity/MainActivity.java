package com.mertmsrl.intentactivity;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnGetTime, btnGoToActivity2, btnGoToActivity3, btnGoToActivity4, btnGoToActivity5, btnGoToSharedPreferences, btnSendNotification;
    TextView textViewTime;
    //    TimePicker timePicker;
    Spinner spinnerCities, spinnerTowns;
    Context context = this;
    int globalHour, globalMinute;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        btnListener();
//        btnGetTimeListener();



//        setSpinnerData();

    }


    public void init() {
//        btnGetTime = findViewById(R.id.btnGetTime);
        btnGoToActivity2 = findViewById(R.id.btnGoToActivity2);
        btnGoToActivity3 = findViewById(R.id.btnGoToActivity3);
        btnGoToActivity4 = findViewById(R.id.btnGoToActivity4);
        btnGoToActivity5 = findViewById(R.id.btnGoToActivity5);
        btnGoToSharedPreferences = findViewById(R.id.btnGoToSharedPreferences);
        btnSendNotification =findViewById(R.id.btnSendNotification);

        textViewTime = findViewById(R.id.textViewTime);

//        timePicker = findViewById(R.id.timePicker);

//        spinnerCities = findViewById(R.id.spinnerCities);
//        spinnerTowns = findViewById(R.id.spinnerTowns);
    }

    public void btnListener(){
        btnGoToActivity2Listener();
        btnGoToActivity3Listener();
        btnGotoActivity4Listener();
        btnGoToActivity5Listener();
        btnGoToSharedPreferencesListener();
        btnSendNotificationListener();
    }

//    public void btnGetTimeListener() {
//
//        Calendar calendar = Calendar.getInstance();
//        globalHour = calendar.get(Calendar.HOUR);
//        globalMinute = calendar.get(Calendar.MINUTE);
//
////        btnGetTime.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                TimePickerDialog timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
////                    @Override
////                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
////                        textViewTime.setText("hour : " + hourOfDay + " minute : " + minute);
////
//////                        timePicker.setHour(hourOfDay);
//////                        timePicker.setMinute(minute);
////                    }
////
////                }, globalHour, globalMinute, true);
////                timePickerDialog.show();
////
////            }
////        });
//
//    }


    public void btnSendNotificationListener(){



        btnSendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationManager manager;
                String page = "https://github.com";
                Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher_round);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(page));
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                NotificationCompat.Builder builder;
                builder = new NotificationCompat.Builder(context)
                        .setLargeIcon(largeIcon)
                        .setContentTitle("bildirim")
                        .setContentText("Icerik")
                        .setTicker("Bildirim Geldi")
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                        .setContentIntent(pendingIntent);


                manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(1, builder.build());



            }
        });

    }

    public void btnGoToActivity2Listener() {

        btnGoToActivity2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity2.class);

                startActivity(intent);
            }
        });
    }

    public void btnGoToActivity3Listener() {

        btnGoToActivity3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity3.class);

                startActivity(intent);
            }
        });
    }

    public void btnGotoActivity4Listener() {

        btnGoToActivity4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity4.class);

                startActivity(intent);
            }
        });
    }

    public void btnGoToActivity5Listener() {

        btnGoToActivity5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity5.class);

                startActivity(intent);
            }
        });
    }

    public void btnGoToSharedPreferencesListener() {
        btnGoToSharedPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity6shared.class);

                startActivity(intent);
            }
        });
    }




//    public void setSpinnerData() {
//
//        List<String> cities = new ArrayList<>();
//        cities.add("Istanbul");
//        cities.add("Ankara");
//
//        cities.add("Izmır");
//        cities.add("Tokat");
//        cities.add("Burdur");
//        cities.add("Sivas");
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item,cities);
//
//        spinnerCities.setAdapter(arrayAdapter);
//
//
//
//        HashMap<String, String[]> hashMap = new HashMap<>();
//        String[] ankara = {
//                "Akyurt",
//                "Altındağ",
//                "Ayaş",
//                "Balâ",
//                "Beypazarı",
//                "Çamlıdere",
//                "Çankaya",
//                "Çubuk",
//                "Elmadağ",
//                "Etimesgut",
//                "Evren",
//                "Gölbaşı",
//                "Güdül",
//                "Haymana",
//                "Kalecik",
//                "Kahramankazan",
//                "Keçiören",
//                "Kızılcahamam",
//                "Mamak",
//                "Nallıhan",
//                "Polatlı",
//                "Pursaklar",
//                "Sincan",
//                "Şereflikoçhisar",
//                "Yenimahalle"};
//
//
//        hashMap.put("ankara",ankara);
//
//        spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String cityName = parent.getItemAtPosition(position).toString();
//                if (cityName.equalsIgnoreCase("ankara")){
//                    String[] townsList = hashMap.get("ankara");
//                    setDataOfSpinnerTowns(spinnerTowns, townsList);
//                    spinnerTowns.setVisibility(View.VISIBLE);
//
//                }
//                else{
//                    spinnerTowns.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

//    public void setDataOfSpinnerTowns(Spinner spinnerTowns, String[] towns){
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1,towns);
//        spinnerTowns.setAdapter(arrayAdapter);
//
//    }


}