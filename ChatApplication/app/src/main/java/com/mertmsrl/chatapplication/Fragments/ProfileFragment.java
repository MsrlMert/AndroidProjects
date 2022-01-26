package com.mertmsrl.chatapplication.Fragments;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mertmsrl.chatapplication.Activities.MainActivity;
import com.mertmsrl.chatapplication.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment {

    Context context;
    ImageView imageViewUserImg;
    TextView textViewUserName;

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    String currentUid;
    Uri imageUri;

    Bitmap returnedImage;
    ImageDecoder.Source imgSrc;

    StorageReference storageReference,storageReference2, newStorageReference;
    String savePlace, downloadUrl;

    String currentUImg;

    byte[] imgByte;

    public ProfileFragment(Context context) {
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        currentUid = firebaseUser.getUid();
        this.context = context;

        storageReference = FirebaseStorage.getInstance().getReference();
    }
    public interface UserNameFirestoreListener {

        void onResponse(String userName);

        void onError(String error);
    }

    public interface UserImgFirestoreListener{
        void onResponse(String imageId);

        void onError(String error);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        imageViewUserImg = view.findViewById(R.id.profile_fragment_user_img);
        textViewUserName = view.findViewById(R.id.profile_fragment_txt_view_user_name);

        getUserName(new UserNameFirestoreListener() {
            @Override
            public void onResponse(String userName) {
                textViewUserName.setText(userName);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

            }
        });

        getUserImg(new UserImgFirestoreListener() {
            @Override
            public void onResponse(String imageId) {
                if (imageId.equals("default")){

                }else
                    Picasso.get().load(imageId).resize(100,100).into(imageViewUserImg);

            }

            @Override
            public void onError(String error) {
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            }
        });

        addImgListener();

        return view;
    }

    public void getUserName(UserNameFirestoreListener userNameFirestoreListener) {
        firestore.collection("users").document(currentUid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String userName = Objects.requireNonNull(documentSnapshot.get("userName")).toString();
                        userNameFirestoreListener.onResponse(userName);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                userNameFirestoreListener.onError(e.getMessage());
            }
        });
    }

    public void addImgListener() {
        imageViewUserImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting image from the gallery

                // permission not given
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                // permission granted make action
                else {
                    goToGalleryIntent();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            goToGalleryIntent();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();

            try {
                if (Build.VERSION.SDK_INT >= 28){
                    imgSrc = ImageDecoder.createSource(context.getContentResolver(), imageUri);
                    returnedImage = ImageDecoder.decodeBitmap(imgSrc);
                }
                else {
                    returnedImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);

                }

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                returnedImage.compress(Bitmap.CompressFormat.PNG, 75, outputStream);
                imgByte = outputStream.toByteArray();

                savePlace = "users/"+currentUid+"/profile.png";

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

                                                // update user profile photo in firestore
                                                HashMap<String, Object> hashMap = new HashMap<>();
                                                hashMap.put("imageId", downloadUrl);
                                                firestore.collection("users").document(currentUid)
                                                        .update(hashMap)
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()){
                                                                    Toast.makeText(context, "Photo Changed Successfully", Toast.LENGTH_SHORT).show();
                                                                    Picasso.get().load(downloadUrl).resize(100,100).into(imageViewUserImg);
                                                                }
                                                            }
                                                        });
                                            }
                                        });
                            }
                        });

            }catch (Exception e){

            }
        }
    }

    public void goToGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 2);
    }

    public void getUserImg(UserImgFirestoreListener userImgFirestoreListener){
        firestore.collection("users").document(currentUid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            currentUImg = documentSnapshot.get("imageId").toString();
                            userImgFirestoreListener.onResponse(currentUImg);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                userImgFirestoreListener.onError(e.getMessage());
            }
        });
    }

}