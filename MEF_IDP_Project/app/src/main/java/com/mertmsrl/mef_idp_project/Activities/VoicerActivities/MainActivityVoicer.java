package com.mertmsrl.mef_idp_project.Activities.VoicerActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.mertmsrl.mef_idp_project.Activities.LoginActivity;
import com.mertmsrl.mef_idp_project.Adapters.TabsAdapter;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.HistoryFragment;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.MatchedStudentFragment;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.SearchStudentFragment;
import com.mertmsrl.mef_idp_project.R;

public class MainActivityVoicer extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

//    BottomNavigationView bottomNavigationView;

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    ImageView imgViewAddRecord;
    Toolbar toolbar;
    Context context = this;

    TabLayout tabLayout;
    ViewPager viewPager;
    TabsAdapter tabsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_voicer);
        init();
//        bottomNavViewListener();
    }

    public void init(){
//        bottomNavigationView = findViewById(R.id.content_main_voicer_bottom_nav_menu);
        fragmentManager = getSupportFragmentManager();
        tabLayout = findViewById(R.id.content_main_voicer_tab_layout);
        viewPager = findViewById(R.id.content_main_voicer_view_pager);
        tabsAdapter = new TabsAdapter(fragmentManager, context);
        toolbar = findViewById(R.id.content_main_voicer_tool_bar);
        setSupportActionBar(toolbar);

        viewPager.setAdapter(tabsAdapter);
        tabLayout.setupWithViewPager(viewPager);

        firebaseAuth = FirebaseAuth.getInstance();

    }

//    public void bottomNavViewListener(){
//        tabLayout.setOnClickListener(new  {
//        } {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.voicer_content_main_bottom_nav_menu_matched_students:
//                        MatchedStudentFragment matchedStudentFragment = new MatchedStudentFragment(fragmentManager, context);
//                        setFragment(matchedStudentFragment);
//                        imgViewAddRecord.setVisibility(View.GONE);
//                        return true;
//
//                    case R.id.voicer_content_main_bottom_nav_menu_all_students:
//                        SearchStudentFragment searchStudentFragment = new SearchStudentFragment(context);
//                        setFragment(searchStudentFragment);
//                        return true;
//
//                    case R.id.voicer_content_main_bottom_nav_menu_history:
//                        HistoryFragment historyFragment = new HistoryFragment(fragmentManager, context);
//                        setFragment(historyFragment);
//                        return true;
//                }
//
//                return false;
//            }
//        });
//    }

//    public void setFragment(Fragment fragment){
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.content_main_voicer_frame_layout, fragment);
//        fragmentTransaction.commit();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.options_menu_logout){
            firebaseAuth.signOut();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        return true;
    }


}