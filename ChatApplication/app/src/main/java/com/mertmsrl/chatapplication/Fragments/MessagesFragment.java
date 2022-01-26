package com.mertmsrl.chatapplication.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.mertmsrl.chatapplication.Adapters.FriendsListRecyclerViewAdapter;
import com.mertmsrl.chatapplication.Classes.FriendsItems;
import com.mertmsrl.chatapplication.R;

import java.util.ArrayList;


public class MessagesFragment extends Fragment {


    ArrayList<FriendsItems> friendsItemsArrayList;

    FirebaseUser firebaseUser;
    FirebaseFirestore firestore;
    RecyclerView recyclerViewMessageRequests;

    String friendName;
    String friendUserId;

    Context context;


    public MessagesFragment(Context context) {
        // Required empty public constructor
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        friendsItemsArrayList = new ArrayList<>();

        this.context = context;


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        recyclerViewMessageRequests = view.findViewById(R.id.messages_fragment_recycler_view);
        addFriendsToArrayList();
        return view;
    }

    public void addFriendsToArrayList() {
        firestore.collection("users").document(firebaseUser.getUid())
                .collection("channel")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            friendName = document.get("userName").toString();
                            friendUserId = document.get("userId").toString();

                            FriendsItems friendsItems = new FriendsItems(friendUserId, friendName);
                            friendsItemsArrayList.add(friendsItems);

                        }
                        setFriendsRecyclerViewData();
                    }

                });
    }


    public void setFriendsRecyclerViewData() {
        FriendsListRecyclerViewAdapter adapter = new FriendsListRecyclerViewAdapter(context, friendsItemsArrayList);
        LinearLayoutManager manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerViewMessageRequests.setLayoutManager(manager);
        recyclerViewMessageRequests.setAdapter(adapter);
    }




}