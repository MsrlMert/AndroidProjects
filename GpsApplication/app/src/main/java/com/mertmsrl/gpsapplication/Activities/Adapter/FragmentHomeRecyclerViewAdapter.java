package com.mertmsrl.gpsapplication.Activities.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertmsrl.gpsapplication.Activities.ActivityMap;
import com.mertmsrl.gpsapplication.Activities.Models.Users;
import com.mertmsrl.gpsapplication.R;

import java.util.ArrayList;

public class FragmentHomeRecyclerViewAdapter extends RecyclerView.Adapter<FragmentHomeRecyclerViewAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Users> usersArrayList;
    FirebaseFirestore firestore;
    FirebaseUser currentUser;
    final String USER_COLLECTION_NAME = "users";


    public FragmentHomeRecyclerViewAdapter(Context context, ArrayList<Users> usersArrayList) {
        this.context = context;
        this.usersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_home_recycler_view_row, parent, false);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String userEmail = usersArrayList.get(position).getUserEmail();


        holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        holder.textViewUserEmail.setText(userEmail);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting target user location
                try {
                    String targetId = usersArrayList.get(position).getUserId();
                    Toast.makeText(context, targetId, Toast.LENGTH_SHORT).show();
                    firestore.collection(USER_COLLECTION_NAME).document(targetId).collection("UserLocation").document(targetId)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.get("lat") != null && documentSnapshot.get("lng") != null){
                                        double lat = (double) documentSnapshot.get("lat");
                                        double lng = (double) documentSnapshot.get("lng");

                                        Intent intent = new Intent(context, ActivityMap.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putDouble("lat", lat);
                                        bundle.putDouble("lng", lng);
                                        intent.putExtras(bundle);

                                        context.startActivity(intent);
                                    }else{
                                        Toast.makeText(context, "Location is Empty", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewUserEmail;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.fragment_home_recycler_view_row_img);
            textViewUserEmail = itemView.findViewById(R.id.fragment_home_recycler_view_row_user_email);
            relativeLayout = itemView.findViewById(R.id.fragment_home_recycler_view_row_relative);
        }
    }
}
