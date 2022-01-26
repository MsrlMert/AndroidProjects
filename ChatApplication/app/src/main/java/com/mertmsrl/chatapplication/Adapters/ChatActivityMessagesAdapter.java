package com.mertmsrl.chatapplication.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mertmsrl.chatapplication.Classes.Messages;
import com.mertmsrl.chatapplication.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatActivityMessagesAdapter extends RecyclerView.Adapter<ChatActivityMessagesAdapter.ViewHolder> {

    private final int VIEW_RIGHT = 1;
    private final int VIEW_LEFT = 0;
    ArrayList<Messages> messagesArrayList;
    Context context;
    LayoutInflater inflater;

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    String currentUid;


    public ChatActivityMessagesAdapter(ArrayList<Messages> messagesArrayList, Context context) {
        this.messagesArrayList = messagesArrayList;
        this.context = context;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUid = firebaseUser.getUid();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(context);
        if (viewType == VIEW_RIGHT){
            View view = inflater.inflate(R.layout.activity_chat_recycler_view_row_right_message, parent, false);
            return new ViewHolder(view);
        }
        else if (viewType == VIEW_LEFT){
            View view = inflater.inflate(R.layout.activity_chat_recycler_view_row_left_message, parent, false);
            return new ViewHolder(view);

        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int adapterPosition = holder.getAdapterPosition();
        Messages message = messagesArrayList.get(adapterPosition);

        if (message.getMessageType().equals("text")){
            holder.progressBar.setVisibility(View.GONE);
            holder.imageViewMessage.setVisibility(View.GONE);
            String messageContent = messagesArrayList.get(adapterPosition).getMessageContent();
            holder.textViewMessage.setText(messageContent);


            if (message.getSenderId().equals(currentUid) && message.isMessageIsRead()){
                holder.imageViewImgMessageRead.setVisibility(View.VISIBLE);
            }
            if (message.getSenderId().equals(currentUid) && !message.isMessageIsRead()){
                holder.imageViewImgMessageSent.setVisibility(View.VISIBLE);
            }


        }else {
            holder.textViewMessage.setVisibility(View.GONE);
            Picasso.get().load(message.getMessageContent()).resize(200,200).into(holder.imageViewMessage, new Callback() {
                @Override
                public void onSuccess() {
                    holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageViewMessage;
        ImageView imageViewImgMessageSent, imageViewImgMessageForwarded, imageViewImgMessageRead;
        ImageView imageViewTxtMessageSent, imageViewTxtMessageForwarded, imageViewTxtMessageRead;
        TextView textViewMessage;
        ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessage = itemView.findViewById(R.id.activity_chat_recycler_view_row_message_text_view);
            imageViewMessage = itemView.findViewById(R.id.activity_chat_recycler_view_row_message_img_view);
            progressBar = itemView.findViewById(R.id.activity_chat_progress_bar);

            imageViewImgMessageSent = itemView.findViewById(R.id.activity_chat_img_message_sent);
            imageViewImgMessageForwarded = itemView.findViewById(R.id.activity_chat_img_message_forwarded);
            imageViewImgMessageRead = itemView.findViewById(R.id.activity_chat_img_message_read);

            imageViewTxtMessageSent = itemView.findViewById(R.id.activity_chat_txt_message_sent);
            imageViewTxtMessageForwarded = itemView.findViewById(R.id.activity_chat_txt_message_forwarded);
            imageViewImgMessageRead = itemView.findViewById(R.id.activity_chat_txt_message_read);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messagesArrayList.get(position).getSenderId().equals(currentUid)){
            return VIEW_RIGHT;
        }
        else
            return VIEW_LEFT;
    }
}
