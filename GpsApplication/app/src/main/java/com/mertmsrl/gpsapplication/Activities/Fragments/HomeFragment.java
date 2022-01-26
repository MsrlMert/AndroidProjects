package com.mertmsrl.gpsapplication.Activities.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mertmsrl.gpsapplication.Activities.Adapter.FragmentHomeRecyclerViewAdapter;
import com.mertmsrl.gpsapplication.Activities.MainActivity;
import com.mertmsrl.gpsapplication.Activities.Models.Users;
import com.mertmsrl.gpsapplication.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {


    RecyclerView recyclerViewUsers;
    ArrayList<Users> usersArrayList;
    Context context;
    Query query;
    final String USER_COLLECTION_NAME = "users";
    FirebaseUser currentUser;

    public HomeFragment(Context context) {

        this.context = context;
        query = FirebaseFirestore.getInstance().collection(USER_COLLECTION_NAME);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        usersArrayList = new ArrayList<>();
        getUsers();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewUsers = view.findViewById(R.id.fragment_home_recycler_view);
        getUsers();
        FragmentHomeRecyclerViewAdapter adapter = new FragmentHomeRecyclerViewAdapter(context, usersArrayList);
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerViewUsers.setLayoutManager(manager);
        recyclerViewUsers.setAdapter(adapter);

        return view;
    }


    public void getUsers(){
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null){
                    usersArrayList.clear();

                    for (DocumentSnapshot document : value.getDocuments()){
                        String userId = document.get("userId").toString();
                        if (!userId.equals(currentUser.getUid())){
                            String email = document.get("userEmail").toString();
                            String password = document.get("userPassword").toString();
                            Users user = new Users(userId, email, password);
                            usersArrayList.add(user);
                        }

                    }
                }
            }
        });
    }



}