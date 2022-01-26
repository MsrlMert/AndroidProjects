package com.mertmsrl.chatapplication.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mertmsrl.chatapplication.Adapters.ChatActivityMessagesAdapter;
import com.mertmsrl.chatapplication.Classes.Messages;
import com.mertmsrl.chatapplication.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {


    TextView textViewFriendName, textViewFriendLastLoginDate;

    ImageButton imgButtonBack;

    ArrayList<Messages> messagesArrayList;

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    String currentUid;

    Query query;
    Context context = this;

    LinearLayoutManager manager;
    RecyclerView recyclerViewChat;



    String friendId, friendName;


    EditText editTextMessage;
    ImageView imgViewSendMessage, imgViewMessageAddImage;
    static boolean active = false;
    Uri imageUri;
    ImageDecoder.Source imgSrc;
    Bitmap returnedImg;
    byte[] imgByte;
    StorageReference storageReference, storageReference2, newStorageReference;
    String savePlace, downloadUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        imgViewMessageAddImageListener();
        storageReference = FirebaseStorage.getInstance().getReference();
    }


    @Override
    public void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
    }

    public interface FirebaseFindChatIdListener {
        void onResponse(String chatId);

        void onError(String error);
    }

    public interface FirebaseUserStatue{
        void onResponse(String userStatue);

        void onError(String error);
    }

    public void init() {
        recyclerViewChat = findViewById(R.id.chat_activity_recycler_view);

        firestore = FirebaseFirestore.getInstance();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        assert firebaseUser != null;
        currentUid = firebaseUser.getUid();

        Intent intent = getIntent();
        friendId = intent.getStringExtra("friendUserId");
        friendName = intent.getStringExtra("friendName");

        editTextMessage = findViewById(R.id.chat_activity_edit_text_message);
        imgButtonBack = findViewById(R.id.chat_activity_img_back);
        imgViewSendMessage = findViewById(R.id.chat_activity_img_view_send_message);
        imgViewMessageAddImage = findViewById(R.id.chat_activity_message_add_img_view);

        textViewFriendName = findViewById(R.id.chat_activity_friend_name);
        textViewFriendLastLoginDate = findViewById(R.id.chat_activity_friend_last_login_date);


        // Set Chat Activity Friend Data
        textViewFriendName.setText(friendName);

        messagesArrayList = new ArrayList<>();

        getChatMessages();
        getUserStatue(new FirebaseUserStatue() {
            @Override
            public void onResponse(String userStatue) {
                textViewFriendLastLoginDate.setText(userStatue);
            }

            @Override
            public void onError(String error) {

            }
        });



//        setRecyclerViewData();


    }

    public void getChatMessages() {
        findChatId(new FirebaseFindChatIdListener() {
            @Override
            public void onResponse(String chatId) {
                query = firestore.collection("ChatMessages").document(chatId).collection("Messages");

                query.orderBy("messageTime", Query.Direction.ASCENDING)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                if (error != null) {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                if (value != null) {
                                    messagesArrayList.clear();

                                    for (DocumentSnapshot document : value.getDocuments()) {
                                        Messages message = document.toObject(Messages.class);

                                        assert message != null;
                                        if (message.getSenderId().equals(friendId) && active){
                                            HashMap<String, Object> hashMap = new HashMap<>();
                                            hashMap.put("messageIsRead", true);
                                            document.getReference().update(hashMap);
                                        }
                                        messagesArrayList.add(message);
                                    }
                                    setRecyclerViewData();
                                }
                            }
                        });
            }

            @Override
            public void onError(String error) {

            }
        });


    }

    public void findChatId(FirebaseFindChatIdListener firebaseFindChatIdListener) {

        firestore.collection("users").document(currentUid).collection("ChatId").document(friendId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            String chatId = documentSnapshot.get("channelId").toString();

                            firebaseFindChatIdListener.onResponse(chatId);

                        }
                    }
                });
    }

    public void setRecyclerViewData() {

        ChatActivityMessagesAdapter adapter = new ChatActivityMessagesAdapter(messagesArrayList, context);
        manager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerViewChat.setLayoutManager(manager);
        recyclerViewChat.scrollToPosition(messagesArrayList.size() - 1);
        recyclerViewChat.setAdapter(adapter);

    }

    public void imgViewMessageAddImageListener() {
        imgViewMessageAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ChatActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                } else
                    goToGalleryIntent();
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            goToGalleryIntent();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                if (Build.VERSION.SDK_INT >= 28) {
                    imgSrc = ImageDecoder.createSource(context.getContentResolver(), imageUri);
                    returnedImg = ImageDecoder.decodeBitmap(imgSrc);
                } else
                    returnedImg = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                returnedImg.compress(Bitmap.CompressFormat.PNG, 75, outputStream);
                imgByte = outputStream.toByteArray();


                findChatId(new FirebaseFindChatIdListener() {
                    @Override
                    public void onResponse(String chatId) {
                        String randomMessageCode = UUID.randomUUID().toString();
                        savePlace = "messages/"+chatId+"/"+randomMessageCode+".png";

                        storageReference2 = storageReference.child(savePlace);
                        storageReference2.putBytes(imgByte)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        newStorageReference = FirebaseStorage.getInstance().getReference(savePlace);
                                        newStorageReference.getDownloadUrl()
                                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        downloadUrl = uri.toString();
                                                        sendMessage("image", downloadUrl);
                                                    }
                                                });
                                    }
                                });
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
//                savePlace = "users/" + currentUid + "/messages/" + friendId;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void goToGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    public void imgViewMessageSend(View view) {
        sendMessage("text", editTextMessage.getText().toString());
    }

    public void sendMessage(String messageType, String messageContent){

        findChatId(new FirebaseFindChatIdListener() {
            @Override
            public void onResponse(String chatId) {
                String messageDocId = UUID.randomUUID().toString();
                Date currentTime = Calendar.getInstance().getTime();
                Timestamp messageTime = new Timestamp(currentTime);

                Messages message = new Messages(messageType, messageDocId, friendId, currentUid, messageContent, messageTime, false);
                firestore.collection("ChatMessages").document(chatId).collection("Messages").document(message.getMessageDocId())
                        .set(message)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(context, "Message Send", Toast.LENGTH_SHORT).show();
                                    editTextMessage.setText("");
                                } else {
                                    Toast.makeText(context, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void getUserStatue(FirebaseUserStatue firebaseUserStatue){
        firestore.collection("users").document(currentUid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                             String userStatue = documentSnapshot.get("userStatue").toString();
                            firebaseUserStatue.onResponse(userStatue);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                firebaseUserStatue.onError(e.getMessage());
            }
        });
    }

    public void imgBtnBackOnClick(View view) {
        super.onBackPressed();

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}