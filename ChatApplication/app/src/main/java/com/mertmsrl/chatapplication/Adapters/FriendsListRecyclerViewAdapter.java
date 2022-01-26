package com.mertmsrl.chatapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.mertmsrl.chatapplication.Activities.ChatActivity;
import com.mertmsrl.chatapplication.Classes.FriendsItems;
import com.mertmsrl.chatapplication.R;

import java.util.ArrayList;

public class FriendsListRecyclerViewAdapter extends RecyclerView.Adapter<FriendsListRecyclerViewAdapter.ViewHolder> {


    Context context;
    LayoutInflater inflater;
    ArrayList<FriendsItems> friendsItemsArrayList;

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    String currentUid;
    Query query;

    String lastMessageContentText = "";

    public FriendsListRecyclerViewAdapter(Context context, ArrayList<FriendsItems> friendsItemsArrayList) {
        this.context = context;
        this.friendsItemsArrayList = friendsItemsArrayList;
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        currentUid = firebaseUser.getUid();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_messages_recycler_view_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        int adapterPosition = holder.getAdapterPosition();


        String friendUserId = friendsItemsArrayList.get(adapterPosition).getFriendUserId();
        String friendUserName = friendsItemsArrayList.get(adapterPosition).getFriendName();

        firestore.collection("users").document(currentUid).collection("ChatId").document(friendUserId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            String chatId = documentSnapshot.get("channelId").toString();

                            query = firestore.collection("ChatMessages").document(chatId).collection("Messages");

                            query.orderBy("messageTime", Query.Direction.DESCENDING)
                                    .limit(1)
                                    .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null){
                                                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                            if (value != null){

                                                for (DocumentSnapshot document : value.getDocuments()){
                                                    lastMessageContentText = document.get("messageContent").toString();

                                                }
                                                holder.imageViewUserImg.setImageResource(R.mipmap.ic_launcher_round);
                                                holder.textViewUserName.setText(friendUserName);
                                                holder.textViewLastMessageText.setText(lastMessageContentText);

                                                relativeLayoutListener(holder.relativeLayoutMain, friendUserId, friendUserName);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }

    public void relativeLayoutListener(RelativeLayout relativeLayout, String friendUserId, String friendName) {
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ChatActivity.class);

                intent.putExtra("friendUserId", friendUserId);
                intent.putExtra("friendName", friendName);

                context.startActivity(intent);
            }
        });
    }

    public void getChatLastMessage(String friendUid) {
//        final String[] lastMessageContent = new String[1];
        query = firestore.collection("ChatMessages").document(currentUid).collection("messages").document(friendUid).collection("MessagesDoc");

        query.orderBy("messageTime", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        if (value != null) {

                            for (DocumentSnapshot document : value.getDocuments()) {
                                lastMessageContentText = document.get("messageContent").toString();
//                                lastMessageContent[0] = document.get("messageContent").toString();
                            }

                        }
                    }
                });
    }


    @Override
    public int getItemCount() {
        return friendsItemsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewUserImg;
        TextView textViewUserName, textViewNewMessageCount, textViewLastMessageText, textViewLastMessageDate;
        RelativeLayout relativeLayoutMain;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayoutMain = itemView.findViewById(R.id.messages_fragment_recycler_view_row_main_relative_layout);

            imageViewUserImg = itemView.findViewById(R.id.messages_fragment_recycler_view_row_user_img);

            textViewUserName = itemView.findViewById(R.id.messages_fragment_recycler_view_row_relative_layout_user_name);
            textViewNewMessageCount = itemView.findViewById(R.id.messages_fragment_recycler_view_row_relative_layout_new_message_count);
            textViewLastMessageText = itemView.findViewById(R.id.messages_fragment_recycler_view_row_relative_layout_last_message);
            textViewLastMessageDate = itemView.findViewById(R.id.messages_fragment_recycler_view_row_relative_layout_last_message_date);
        }
    }


}
