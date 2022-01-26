package com.mertmsrl.chatapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mertmsrl.chatapplication.Adapters.CustomMessageRequestsAdapter;
import com.mertmsrl.chatapplication.Classes.MessageRequest;
import com.mertmsrl.chatapplication.Classes.Users;
import com.mertmsrl.chatapplication.Fragments.MessagesFragment;
import com.mertmsrl.chatapplication.Fragments.ProfileFragment;
import com.mertmsrl.chatapplication.Fragments.UsersFragment;
import com.mertmsrl.chatapplication.R;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    // Fragment Actions Definitions
    
    HashMap<String, Object> hashMapLastLoginDate;
    Dialog dialogMessageRequest;
    RecyclerView recyclerViewMessageRequests;
    Window window;

    RelativeLayout relativeLayoutNotify;
    String currentUserId;

    Toolbar toolbar;
    // Fragment Definitions
    UsersFragment usersFragment;
    MessagesFragment messagesFragment;
    ProfileFragment profileFragment;

    // For Firebase Definitions
    ArrayList<Users> messageRequestsArrayList;
    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    FirebaseAuth firebaseAuth;
    Query query;

    Context context = this;

    NavigationView navigationView;
    BottomNavigationView bottomNavigationView;

    HashMap<String, Object> hashMapStatue;

    String imageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        bottomNavViewListener();
        navigationViewListener();
        relativeLayoutNotifyOnClick();
        setRecyclerViewMessageRequestsData();
        setUserStatue("online");

    }

    public void init() {

        navigationView = findViewById(R.id.navigation_view);
        bottomNavigationView = findViewById(R.id.content_main_bottom_nav_view);

        dialogMessageRequest = new Dialog(context, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialogMessageRequest.setContentView(R.layout.custom_dialog_message_requests_box);
        recyclerViewMessageRequests = dialogMessageRequest.findViewById(R.id.custom_dialog_message_request_box_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerViewMessageRequests.setLayoutManager(manager);

        toolbar = findViewById(R.id.content_main_tool_bar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Chat Application");

        currentUserId = FirebaseAuth.getInstance().getUid();
        usersFragment = new UsersFragment(context, currentUserId);
        setFragment(usersFragment);

        relativeLayoutNotify = findViewById(R.id.content_main_relative_layout_notify);

        messageRequestsArrayList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        hashMapStatue = new HashMap<>();


        hashMapLastLoginDate = new HashMap<>();


    }


    public void setRecyclerViewMessageRequestsData(){
        query = firestore.collection("MessageRequests").document(firebaseUser.getUid()).collection("request");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value != null) {
                    messageRequestsArrayList.clear();


                    for (DocumentSnapshot snp : value.getDocuments()) {
                        MessageRequest messageRequest = snp.toObject(MessageRequest.class);


                        // Find Data Of Sender
                        firestore.collection("users").document(messageRequest.getCurrentUserId())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                                int imgRes = (int) documentSnapshot.get("imageId");

                                        String userName = documentSnapshot.get("userName").toString();
                                        String userEmail = documentSnapshot.get("userEmail").toString();
                                        String userPassword = documentSnapshot.get("userPassword").toString();
                                        imageId = documentSnapshot.get("imageId").toString();
                                        Users user = new Users(messageRequest.getCurrentUserId(), userName, userEmail, userPassword, imageId);
                                        messageRequestsArrayList.add(user);
//                                                Toast.makeText(context, "size inner " + String.valueOf(messageRequestsArrayList.size()), Toast.LENGTH_SHORT).show();
//                                                CustomMessageRequestsAdapter adapter = new CustomMessageRequestsAdapter(messageRequestsArrayList, context);
//                                                recyclerViewMessageRequests.setAdapter(adapter);

                                    }
                                });

                    }


                }
            }
        });

    }

    public void relativeLayoutNotifyOnClick() {
        relativeLayoutNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomMessageRequestsAdapter adapter = new CustomMessageRequestsAdapter(messageRequestsArrayList, context);
                recyclerViewMessageRequests.setAdapter(adapter);

                dialogMessageRequest.show();

            }
        });
    }


    public void bottomNavViewListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.bottom_nav_view_users:
                        usersFragment = new UsersFragment(context, currentUserId);
                        setFragment(usersFragment);
                        relativeLayoutNotify.setVisibility(View.GONE);

                        return true;

                    case R.id.bottom_nav_view_messages:
                        messagesFragment = new MessagesFragment(context);
                        setFragment(messagesFragment);
                        relativeLayoutNotify.setVisibility(View.VISIBLE);

                        return true;
                    case R.id.bottom_nav_view_profile:
                        profileFragment = new ProfileFragment(context);
                        setFragment(profileFragment);
                        relativeLayoutNotify.setVisibility(View.GONE);
                        return true;
                }

                return false;
            }
        });
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.content_main_frame_layout, fragment);
        transaction.commit();

    }

    public void navigationViewListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_view_home:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);
                        return true;

                    case R.id.nav_view_exit:
                        firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        finish();
                        Intent intentLogOut = new Intent(context, LoginActivity.class);
                        startActivity(intentLogOut);
                        return true;
                }


                return false;
            }
        });
    }

    @Override
    protected void onDestroy() {
        hashMapLastLoginDate.put("lastLoginDate", FieldValue.serverTimestamp());
        firestore.collection("users").document(currentUserId).collection("LastLoginDate")
                .document(currentUserId)
                .set(hashMapLastLoginDate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                        }
                    }
                });
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        setUserStatue("offline");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.options_menu_log_out:
                Intent intent = new Intent(context, LoginActivity.class);
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(intent);


                return true;
        }
        return false;
    }


    // this method adjusts the user is active or offline
    public void setUserStatue(String statue){
        hashMapStatue.put("userStatue", statue);
        firestore.collection("users").document(currentUserId)
                .update(hashMapStatue)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

}