package com.mertmsrl.fragmentkullanm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    LinearLayout linearLayoutFragment;
    Button btnSetFragmentA, btnSetFragmentB, btnRemoveFragmentA, btnRemoveFragmentB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        init();
        btnSetFragmentAListener();
        btnSetFragmentBListener();
        btnRemoveFragmentAListener();
        btnRemoveFragmentBListener();
    }

    public void init() {
        linearLayoutFragment = findViewById(R.id.linearLayoutFragment);

        btnSetFragmentA = findViewById(R.id.btnSetFragmentA);
        btnSetFragmentB = findViewById(R.id.btnSetFragmentB);
        btnRemoveFragmentA = findViewById(R.id.btnRemoveFragmentA);
        btnRemoveFragmentB = findViewById(R.id.btnRemoveFragmentB);
    }

    public void btnSetFragmentAListener() {
        btnSetFragmentA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentA fragmentA = new FragmentA();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.add(R.id.linearLayoutFragment, fragmentA, "fragmentA");
                transaction.commit();
            }
        });
    }

    public void btnSetFragmentBListener() {
        btnSetFragmentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentB fragmentB = new FragmentB();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                transaction.add(R.id.linearLayoutFragment, fragmentB, "fragmentB");
                transaction.commit();
            }
        });
    }

    public void btnRemoveFragmentAListener() {
        btnRemoveFragmentA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentA fragmentA = (FragmentA) fragmentManager.findFragmentByTag("fragmentA");

                if (fragmentA != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(fragmentA);
                    transaction.commit();
                }
            }
        });

    }

    public void btnRemoveFragmentBListener() {
        btnRemoveFragmentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentB fragmentB = (FragmentB) fragmentManager.findFragmentByTag("fragmentB");

                if (fragmentB != null) {
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    transaction.remove(fragmentB);
                    transaction.commit();
                }
            }
        });

    }
}