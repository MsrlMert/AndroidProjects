package com.mertmsrl.mef_idp_project.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertmsrl.mef_idp_project.Models.User;
import com.mertmsrl.mef_idp_project.R;

import java.util.HashMap;
import java.util.List;

public class StudentSearchRecyclerAdapter extends RecyclerView.Adapter<StudentSearchRecyclerAdapter.ViewHolder> {


    List<User> userList;
    Context context;
    LayoutInflater layoutInflater;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    FirebaseFirestore firestore;
    FirebaseUser currentUser;
    final private String USERS_COLLECTION_NAME = "users";
    final private String VOICERS_COLLECTION_NAME = "voicers";
    HashMap<String, Object> hashMap;

    public StudentSearchRecyclerAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialog = alertDialogBuilder.create();
        firestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        hashMap = new HashMap<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.student_search_recyler_view_row, parent, false);



        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String userId = userList.get(position).getUserId();
        String userName = userList.get(position).getUserName();
        String userEmail = userList.get(position).getUserEmail();
        String userCourseName = userList.get(position).getUserCourseName();
        String userPassword = userList.get(position).getUserPassword();


        holder.textViewUserName.setText(userName);
        holder.textViewUserEmail.setText(userEmail);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialogBuilder.setTitle("Eşleştirme")
                        .setMessage(userName+" ile eşleşmek istiyor musunuz?")
                        .setCancelable(true)
                        .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                hashMap.put("userId", userId);
                                hashMap.put("userName", userName);
                                hashMap.put("userEmail", userEmail);
                                hashMap.put("userCourseName", userCourseName);
                                hashMap.put("userPassword", userPassword);
                                hashMap.put("voicerId", currentUser.getUid());
                                hashMap.put("voicerEmail", currentUser.getEmail());

                                firestore.collection(USERS_COLLECTION_NAME).document(userId)
                                        .collection("MatchedVoicer")
                                        .document(currentUser.getUid())
                                        .set(hashMap)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    firestore.collection(VOICERS_COLLECTION_NAME).document(currentUser.getUid())
                                                            .collection("MatchedStudent")
                                                            .document(userId)
                                                            .set(hashMap)
                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful()){
                                                                        Toast.makeText(context, "Eşleştirildi", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    else
                                                                        Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            });

                                                }
                                            }
                                        });
                            }
                        })
                        .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView textViewUserEmail, textViewUserName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.student_search_recycler_view_row_relative_layout);

            textViewUserEmail = itemView.findViewById(R.id.student_search_recycler_view_row_user_email);
            textViewUserName = itemView.findViewById(R.id.student_search_recycler_view_row_user_name);

        }
    }
}
