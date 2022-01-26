package com.mertmsrl.gpsapplication.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mertmsrl.gpsapplication.R;


public class ProfileFragment extends Fragment {


    String userEmail;
    TextView textViewUserEmail;
    SwitchCompat switchCompat;
    Context context;

    public ProfileFragment(Context context, String userEmail) {
        this.context = context;
        this.userEmail = userEmail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        textViewUserEmail = view.findViewById(R.id.fragment_profile_user_email);
        switchCompat = view.findViewById(R.id.fragment_profile_switch);

        textViewUserEmail.setText(userEmail);
        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(context, String.valueOf(isChecked), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}