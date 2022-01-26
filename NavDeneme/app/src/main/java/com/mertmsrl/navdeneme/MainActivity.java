package com.mertmsrl.navdeneme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    FragmentHome fragmentHome;
    View navDrawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setToolbar();
        setDrawer();
        setListViewData();
        bottomNavListener();
        navDrawerFragmentListener();
    }

    public void init() {
        fragmentHome = new FragmentHome();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frame_layout, fragmentHome);
        transaction.commit();
        toolbar = findViewById(R.id.tool_bar);

        drawerLayout = findViewById(R.id.drawer_layout);

        listView = findViewById(R.id.drawer_list_view);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        navDrawerFragment = findViewById(R.id.fragment);
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("Merhaba");
        toolbar.setSubtitle("World");
    }

    public void setDrawer() {
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        navigationDrawerFragment.setDrawerToggle(drawerLayout, toolbar);
    }

    public void setListViewData() {

        ArrayList<NavigationItemClass> arrayList = new ArrayList<>();
        arrayList.add(new NavigationItemClass("Home", R.drawable.ic_baseline_home_24));
        arrayList.add(new NavigationItemClass("Help", R.drawable.ic_baseline_help_24));

        CustomNavDrawerListViewAdapter adapter = new CustomNavDrawerListViewAdapter(arrayList, getApplicationContext());

        listView.setAdapter(adapter);

    }

    public void bottomNavListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.custom_bottom_nav_home:
                        FragmentHome fragmentHome = new FragmentHome();
                        Log.i("tag", "home");
                        setFragment(fragmentHome);
                        return true;

                    case R.id.custom_bottom_nav_reply:
                        FragmentReply fragmentReply = new FragmentReply();
                        Log.i("tag", "reply");
                        setFragment(fragmentReply);
                        return true;
                }
                return false;
            }
        });
    }

    public void navDrawerFragmentListener() {
        drawerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setFragment(Fragment fragment) {

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
    }
}