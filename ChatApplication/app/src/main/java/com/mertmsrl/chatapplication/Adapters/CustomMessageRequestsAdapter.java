package com.mertmsrl.chatapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertmsrl.chatapplication.Classes.Users;
import com.mertmsrl.chatapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomMessageRequestsAdapter extends RecyclerView.Adapter<CustomMessageRequestsAdapter.ViewHolder> {

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    DocumentSnapshot documentSnapshotCurrentUser;

    int adapterPosition;

    ArrayList<Users> usersArrayList;
    Context context;
    LayoutInflater inflater;
    HashMap<String, Object> hashMap;

    public CustomMessageRequestsAdapter(ArrayList<Users> usersArrayList, Context context) {
        this.usersArrayList = usersArrayList;
        this.context = context;
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        hashMap = new HashMap<>();
        getCurrentUserData();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_dialog_message_requests_box_recyler_view_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String imgRes = usersArrayList.get(position).getImageId();
        String userName = usersArrayList.get(position).getUserName();

        holder.textViewUserName.setText(userName);
        if (imgRes.equals("default")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        }else {
            Picasso.get().load(imgRes).resize(80,60).into(holder.imageView);
        }

        adapterPosition = holder.getAdapterPosition();

        btnAcceptListener(holder.btnAccept, adapterPosition);
        btnDismissListener(holder.btnDismiss);

    }

    public void btnAcceptListener(Button btnAccept, int adapterPosition) {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // receiver actions
                hashMap.put("userId", firebaseUser.getUid());
                hashMap.put("userName", documentSnapshotCurrentUser.get("userName"));
                hashMap.put("userEmail", documentSnapshotCurrentUser.get("userEmail"));
                hashMap.put("userImage", documentSnapshotCurrentUser.get("imageId"));
                firestore.collection("users").document(usersArrayList.get(adapterPosition).getUserId())
                        .collection("channel").document(firebaseUser.getUid())
                        .set(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Actions For Current User
                                    hashMap = new HashMap<>();
                                    hashMap.put("userId", usersArrayList.get(adapterPosition).getUserId());
                                    hashMap.put("userName", usersArrayList.get(adapterPosition).getUserName());
                                    hashMap.put("userEmail", usersArrayList.get(adapterPosition).getUserEmail());
                                    hashMap.put("userImage", usersArrayList.get(adapterPosition).getImageId());
                                    firestore.collection("users").document(firebaseUser.getUid())
                                            .collection("channel").document(usersArrayList.get(adapterPosition).getUserId())
                                            .set(hashMap)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        // Delete Message Requests
                                                        String currentUserId = firebaseUser.getUid();
                                                        String targetUserId = usersArrayList.get(adapterPosition).getUserId();
                                                        deleteMessageRequest(currentUserId, targetUserId, "Message Request Accepted");
                                                    }
                                                }
                                            });

                                }
                            }
                        });


            }
        });
    }

    public void btnDismissListener(Button btnDismiss) {
        btnDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentUserId = firebaseUser.getUid();
                String targetUserId = usersArrayList.get(adapterPosition).getUserId();
                String resultText = "Message Request Dismissed";
                deleteMessageRequest(currentUserId, targetUserId, resultText);
            }
        });
    }

    public void getCurrentUserData() {
        firestore.collection("users").document(firebaseUser.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        documentSnapshotCurrentUser = documentSnapshot;
                    }
                });
    }

    public void deleteMessageRequest(String currentUserId, String targetUserId, String resultText) {
        firestore.collection("MessageRequests").document(currentUserId)
                .collection("request").document(targetUserId)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            notifyDataSetChanged();
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
        TextView textViewUserName;
        Button btnAccept, btnDismiss;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.custom_dialog_message_request_box_recycler_view_row_user_img);
            textViewUserName = itemView.findViewById(R.id.custom_dialog_message_request_box_recycler_view_row_user_name);
            btnAccept = itemView.findViewById(R.id.custom_dialog_message_request_box_recycler_view_row_accept);
            btnDismiss = itemView.findViewById(R.id.custom_dialog_message_request_box_recycler_view_row_dismiss);

        }
    }
}