package com.mertmsrl.mef_idp_project.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertmsrl.mef_idp_project.Activities.VoicerActivities.RecordActivity;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.RecordFragment;
import com.mertmsrl.mef_idp_project.Models.User;
import com.mertmsrl.mef_idp_project.R;

import java.util.ArrayList;
import java.util.List;

public class MatchedStudentRecyclerAdapter extends RecyclerView.Adapter<MatchedStudentRecyclerAdapter.ViewHolder> {


    Context context;
    LayoutInflater inflater;
    List<User> userList;
    AlertDialog.Builder builder;
    FirebaseFirestore firestore;
    FirebaseUser currentUser;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    final private String USERS_COLLECTION_NAME = "users";
    final private String VOICERS_COLLECTION_NAME = "voicers";



    public MatchedStudentRecyclerAdapter(FragmentManager fragmentManager, List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
        this.fragmentManager = fragmentManager;

        builder = new AlertDialog.Builder(context);
        firestore = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.matched_student_recycler_view_row, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String userId = userList.get(position).getUserId();
        String userName = userList.get(position).getUserName();
        String userEmail = userList.get(position).getUserEmail();

        holder.userName.setText(userName);
        holder.userEmail.setText(userEmail);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setMessage(userEmail + " eşleşilmiş öğrenci için seçenekler")
                        .setCancelable(true)
                        .setPositiveButton("Yeni Kayıt Ekle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // New Record Activity
                                Intent intent = new Intent(context, RecordActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("userId", userId);
                                bundle.putString("userName", userName);
                                bundle.putString("userEmail", userEmail);
                                intent.putExtras(bundle);
                                context.startActivity(intent);

//                                RecordFragment recordFragment = new RecordFragment(fragmentManager, context, userId, userName, userEmail);
//                                fragmentTransaction = fragmentManager.beginTransaction();
//                                fragmentTransaction.replace(R.id.content_main_voicer_frame_layout, recordFragment);
//                                fragmentTransaction.commit();
                            }
                        }).setNegativeButton("Eşleşmeyi Kaldır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firestore.collection(VOICERS_COLLECTION_NAME).document(currentUser.getUid()).collection("MatchedStudent")
                                .document(userId)
                                .delete()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            firestore.collection(USERS_COLLECTION_NAME).document(userId).collection("MatchedVoicer")
                                                    .document(currentUser.getUid())
                                                    .delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(context, "Eşleştirme Kaldırıldı", Toast.LENGTH_SHORT).show();
                                                            } else
                                                                Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    }
                                });

                    }
                });
                builder.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        TextView userName, userEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.matched_student_recycler_view_row_relative_layout);
            userName = itemView.findViewById(R.id.matched_student_recycler_view_row_user_name);
            userEmail = itemView.findViewById(R.id.matched_student_recycler_view_row_user_email);


        }
    }
}
