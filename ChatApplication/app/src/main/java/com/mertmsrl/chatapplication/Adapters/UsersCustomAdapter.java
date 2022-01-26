package com.mertmsrl.chatapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mertmsrl.chatapplication.Classes.Users;
import com.mertmsrl.chatapplication.Fragments.UsersFragment;
import com.mertmsrl.chatapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersCustomAdapter extends RecyclerView.Adapter<UsersCustomAdapter.ViewHolder> {

    ArrayList<Users> usersArrayList;
    Context context;
    LayoutInflater inflater;
    FirebaseUser firebaseUser;


    // Constructor Part
    public UsersCustomAdapter(ArrayList<Users> usersArrayList, Context context) {
        this.usersArrayList = usersArrayList;
        this.context = context;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.users_fragment_recycler_view_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String userId, userName, userEmail, userImageId;



        userName = usersArrayList.get(position).getUserName();
        userEmail = usersArrayList.get(position).getUserEmail();
        userImageId = usersArrayList.get(position).getImageId();

        if (userImageId.equals("default")){
             holder.imageView.setImageResource(R.mipmap.ic_launcher_round);
        }
        else {
            Picasso.get().load(userImageId).resize(70,75).into(holder.imageView);
        }
        holder.textViewUserName.setText(userName);
        holder.textViewUserEmail.setText(userEmail);


        String targetUserUid = usersArrayList.get(position).getUserId();
        relativeLayoutListener(holder.relativeLayout, targetUserUid);

    }

    // When Touch a Person In the User List, this codes will run
    public void relativeLayoutListener(RelativeLayout relativeLayout, String targetUserUid){
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsersFragment usersFragment = new UsersFragment(context, firebaseUser.getUid());
                usersFragment.adjustChatAction(targetUserUid);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewUserName, textViewUserEmail;
        RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.users_fragment_recycler_view_row_img_view_user_img);
            textViewUserEmail = itemView.findViewById(R.id.users_fragment_recycler_view_row_text_view_user_email);
            textViewUserName = itemView.findViewById(R.id.users_fragment_recycler_view_row_text_view_user_name);
            relativeLayout = itemView.findViewById(R.id.users_fragment_recycler_view_row_relative_layout);
        }
    }
}
