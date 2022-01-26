package com.mertmsrl.chatapplication.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.util.TimeFormatException;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
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
import com.google.firebase.firestore.ServerTimestamp;
import com.mertmsrl.chatapplication.Activities.ChatActivity;
import com.mertmsrl.chatapplication.Adapters.UsersCustomAdapter;
import com.mertmsrl.chatapplication.Classes.MessageRequest;
import com.mertmsrl.chatapplication.Classes.Messages;
import com.mertmsrl.chatapplication.Classes.Users;
import com.mertmsrl.chatapplication.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;


public class UsersFragment extends Fragment {

    ArrayList<Users> usersArrayList = new ArrayList<>();

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;

    Dialog dialog;
    String currentUid;

    RecyclerView recyclerViewUsers;
    Context context;
    Query query;


    public UsersFragment(Context context, String currentUid) {
        firestore = FirebaseFirestore.getInstance();

        this.currentUid = currentUid;
        this.context = context;
        dialog = new Dialog(context);
        dialog.setCancelable(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_users, container, false);

        recyclerViewUsers = view.findViewById(R.id.users_fragment_recycler_view);

        setAdapter();
        return view;
    }

    public void setAdapter() {
        query = firestore.collection("users");
        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null) {
                    usersArrayList.clear();
                    for (QueryDocumentSnapshot snp : value) {
                        // checking user id is not equal to current user id
                        if (!snp.get("userId").equals(currentUid)) {
                            Users user = snp.toObject(Users.class);
                            usersArrayList.add(user);
                        }
                    }
                    UsersCustomAdapter adapter = new UsersCustomAdapter(usersArrayList, context);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
                    recyclerViewUsers.setLayoutManager(layoutManager);

                    recyclerViewUsers.setAdapter(adapter);


                }
            }
        });
    }

    public void openChooseDialog(String targetUserId) {
        dialog.setContentView(R.layout.custom_dialog_choose);
        dialog.show();

        EditText editTextMessage = dialog.findViewById(R.id.custom_dialog_choose_edit_text_message);
        Button btnSendMessage = dialog.findViewById(R.id.custom_dialog_choose_btn_send_message);
        Button btnCancelMessage = dialog.findViewById(R.id.custom_dialog_choose_btn_cancel_message);

        btnSendMessageListener(btnSendMessage, targetUserId, editTextMessage);
        btnCancelMessageListener(btnCancelMessage);
    }

    // btn Send Message Actions
    public void btnSendMessageListener(Button btnSendMessage, String targetUserId, EditText editTextMessage) {

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(editTextMessage.getText().toString())) {
//                    String channelId = UUID.randomUUID().toString();
                    MessageRequest messageRequest = new MessageRequest(targetUserId, currentUid);
                    firestore.collection("MessageRequests").document(targetUserId).collection("request").document(currentUid)
                            .set(messageRequest)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        HashMap<String, Object> hashMapChatId = new HashMap<>();
                                        String channelId = UUID.randomUUID().toString();
                                        String messageDocId = UUID.randomUUID().toString();
                                        Date currentTime = Calendar.getInstance().getTime();
                                        Timestamp messageTime = new Timestamp(currentTime);
                                        Messages message = new Messages("text", messageDocId, targetUserId, currentUid, editTextMessage.getText().toString(), messageTime, false);

                                        hashMapChatId.put("channelId", channelId);
//                                        hashMapChatId.put("senderId", currentUid);
//                                        hashMapChatId.put("receiverId", targetUserId);
                                        firestore.collection("users").document(currentUid).collection("ChatId").document(targetUserId)
                                                .set(hashMapChatId)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        HashMap<String, Object> hashMap = new HashMap<>();
                                                        hashMap.put("channelId", channelId);

                                                        firestore.collection("users").document(targetUserId).collection("ChatId").document(currentUid)
                                                                .set(hashMap)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        firestore.collection("ChatMessages").document(channelId).collection("Messages").document(messageDocId)
                                                                                .set(message)
                                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                                        if (task.isSuccessful()){
                                                                                            Toast.makeText(context, "Message Request Send", Toast.LENGTH_SHORT).show();
                                                                                            editTextMessage.setText("");

                                                                                        }
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                }
                            });
                } else
                    Toast.makeText(context, "Message Must be Filled", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void btnCancelMessageListener(Button btnCancelMessage) {
        btnCancelMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();

            }
        });
    }

    public void adjustChatAction(String targetUserUid) {
        firestore.collection("users").document(currentUid).collection("channel").document(targetUserUid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // They are friend, make chat Actions
                            // Get Friend Data

                            firestore.collection("users").document(targetUserUid)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            if (documentSnapshot.exists()) {

                                                try {
                                                    Intent intent = new Intent(context, ChatActivity.class);
                                                    String userName = documentSnapshot.get("userName").toString();
                                                    String userEmail = documentSnapshot.get("userEmail").toString();

                                                    intent.putExtra("friendName", userName);
                                                    intent.putExtra("friendUserId", targetUserUid);

                                                    context.startActivity(intent);

                                                } catch (Exception exception) {
                                                    exception.printStackTrace();
                                                }

                                            }
                                        }
                                    });

                                /*
                                intent.putExtra("friendName", users[0].getUserName());
                                intent.putExtra("friendUid", users[0].getUserId());

                                startActivity(intent);

                                 */
                        } else {
                            // they are not friend, first meeting
                            openChooseDialog(targetUserUid);
                        }
                    }

                });
    }
}


