package com.mertmsrl.tekrarnavigationview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle toggleButton;
    ListView listView;
    ArrayList<CustomNavMenuRowItem> rowItemArrayList;
    Context context = this;
    FragmentManager fragmentManager;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        setToolbar();
        setToggleButton();
        setListViewData();
        navigationViewListListener();
    }

    public void init() {
        toolbar = findViewById(R.id.tool_bar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        listView = findViewById(R.id.custom_nav_menu_list_view);

        frameLayout = findViewById(R.id.content_main_frame_layout);
    }

    public void navigationViewListListener(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        BottomBarHomeFragment homeFragment = new BottomBarHomeFragment();
                        setFragment(homeFragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 1:
                        BottomBarFlightFragment flightFragment = new BottomBarFlightFragment();
                        setFragment(flightFragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        BottomBarCampaignFragment campaignFragment = new BottomBarCampaignFragment();
                        setFragment(campaignFragment);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }
            }
        });
    }

    public void setToggleButton() {

        toggleButton = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_view_open, R.string.nav_view_close);
        drawerLayout.addDrawerListener(toggleButton);
        toggleButton.syncState();
    }

    public void setToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("World");
    }

    public void setListViewData() {
        rowItemArrayList = new ArrayList<>();

        rowItemArrayList.add(new CustomNavMenuRowItem("Home", R.drawable.ic_baseline_add_to_home_screen_24));
        rowItemArrayList.add(new CustomNavMenuRowItem("FlashLight", R.drawable.ic_baseline_flash_auto_24));
        rowItemArrayList.add(new CustomNavMenuRowItem("highlight", R.drawable.ic_baseline_highlight_24));

        CustomNavMenuAdapter adapter = new CustomNavMenuAdapter(rowItemArrayList, context);
        listView.setAdapter(adapter);
    }

    public void setFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.content_main_frame_layout, fragment);
        transaction.commit();
    }


}