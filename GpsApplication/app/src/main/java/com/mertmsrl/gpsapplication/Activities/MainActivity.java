package com.mertmsrl.gpsapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mertmsrl.gpsapplication.Activities.Fragments.HomeFragment;
import com.mertmsrl.gpsapplication.Activities.Fragments.ProfileFragment;
import com.mertmsrl.gpsapplication.Activities.Models.LocationModel;
import com.mertmsrl.gpsapplication.Activities.Models.Users;
import com.mertmsrl.gpsapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FragmentManager manager;
    FragmentTransaction transaction;
    HomeFragment homeFragment;
    ArrayList<Users> usersArrayList;
    final String USER_COLLECTION_NAME = "users";
    Context context = this;
    Query query;
    Toolbar toolbar;
    FirebaseUser currentUser;
    LocationManager locationManager;
    LocationListener locationListener;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        bottomNavListener();
        getLocation();
    }

    public void init() {
        bottomNavigationView = findViewById(R.id.bottom_nav_menu);
        toolbar = findViewById(R.id.content_main_tool_bar);
        setSupportActionBar(toolbar);

        manager = getSupportFragmentManager();

        usersArrayList = new ArrayList<>();

        query = FirebaseFirestore.getInstance().collection(USER_COLLECTION_NAME);

        firestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        transaction = manager.beginTransaction();

        HomeFragment homeFragment = new HomeFragment(context);
        transaction.replace(R.id.frame_layout, homeFragment);
        transaction.commit();
    }


    public void bottomNavListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_nav_home:
                        homeFragment = new HomeFragment(context);
//                            transaction = manager.beginTransaction();
//                            transaction.replace(R.id.frame_layout, homeFragment, "homeFragment");
//                            transaction.commit();
                        changeFragment(homeFragment, "homeFragment");

                        return true;

                    case R.id.bottom_nav_track:
                        try {
                            Intent intent = new Intent(MainActivity.this, CurrentUserMapActivity.class);
                            startActivity(intent);
                        } catch (Exception exception) {
                            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        return true;

                    case R.id.bottom_nav_profile:
                        ProfileFragment profileFragment = new ProfileFragment(context, currentUser.getEmail());
                        changeFragment(profileFragment, "profileFragment");

                        return true;
                }
                return false;
            }
        });
    }

    public void changeFragment(Fragment fragment, String fragmentTag) {
        transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment, fragmentTag);
        transaction.commit();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.options_signOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                goToLoginActivity();
                return true;
        }

        return false;
    }

    public void goToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void getLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();

                    LocationModel locationModel = new LocationModel(lat, lng);
                    firestore.collection(USER_COLLECTION_NAME).document(currentUser.getUid()).collection("UserLocation").document(currentUser.getUid())
                            .set(locationModel)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(context, "Gönderilmedi", Toast.LENGTH_SHORT).show();
                                }
                            });

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    Toast.makeText(context, "GPS is off", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    Toast.makeText(context, "GPS is onn", Toast.LENGTH_SHORT).show();
                }
            };

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // izin verilmedi
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                // izin önceden verilmiş
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 100, locationListener);


            }
        } else {
            Toast.makeText(context, "Please Enter GPS", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 100, locationListener);
                }
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


}