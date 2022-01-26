package com.mertmsrl.mef_idp_project.Fragments.VoicerFragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews.RecordHistoryMatchedStudentsModelView;
import com.mertmsrl.mef_idp_project.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class RecordFragment extends Fragment {

    String userId, userName, userEmail;
    ImageView imageViewRecord, imageViewHistory, imageViewSave;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    Spinner spinner;
    Chronometer chronometer;
    TextView textViewUserName, textViewUserEmail;
    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    StorageReference storageRef;

    Context context;
    boolean isRecording = false;
    boolean isTimeCreated = false;
    boolean isPaused = false;
    MediaRecorder mediaRecorder;
    ArrayList<String> lessonsArrayList;
    String selectedLesson;
    RecordHistoryMatchedStudentsModelView recordHistoryMatchedStudentsModelView;
    long timeWhenStopped = 0;
    String recordFile;

    public RecordFragment(FragmentManager fragmentManager, Context context, String userId, String userName, String userEmail) {
        // Required empty public constructor
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.fragmentManager = fragmentManager;
        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storageRef = FirebaseStorage.getInstance().getReference();
        this.context = context;

//        StorageReference storageReference = firebaseStorage.getReference();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_record, container, false);
        imageViewHistory = view.findViewById(R.id.fragment_record_img_view_record_history);
        imageViewRecord = view.findViewById(R.id.fragment_record_img_view_record_voice);
        imageViewRecord.setTag("mic");
        imageViewRecord.setImageResource(R.drawable.ic_baseline_mic_red_64);
        imageViewSave = view.findViewById(R.id.fragment_record_img_view_record_save);

        lessonsArrayList = new ArrayList<>();
        lessonsArrayList.add("Türkçe");
        lessonsArrayList.add("Matematik");
        lessonsArrayList.add("Tarih");
        lessonsArrayList.add("Fizik");
        lessonsArrayList.add("Kimya");
        lessonsArrayList.add("Biyoloji");
        lessonsArrayList.add("İngilizce");
        spinner = view.findViewById(R.id.fragment_record_spinner_lessons);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, lessonsArrayList);
        spinner.setAdapter(arrayAdapter);
        chronometer = view.findViewById(R.id.fragment_record_chronometer);

        textViewUserName = view.findViewById(R.id.fragment_record_text_view_user_name);
        textViewUserEmail = view.findViewById(R.id.fragment_record_text_view_user_email);

        textViewUserName.setText(userName);
        textViewUserEmail.setText(userEmail);
        imgViewHistoryListener();
        imgViewRecordListener();
        imgViewSaveListener();


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void imgViewHistoryListener() {
        imageViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HistoryFragment historyFragment = new HistoryFragment(fragmentManager, context);
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.activity_record_frame_layout, historyFragment);
                fragmentTransaction.commit();
            }
        });
    }

    public void imgViewRecordListener() {

        imageViewRecord.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                isTimeCreated = true;
                // kayıt başlıyor
                if (imageViewRecord.getTag().equals("mic")) {
                    // kayıt önceden durdurulmuş devam ettirilecek
                    if (isPaused) {
                        chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                        chronometer.start();
                        Toast.makeText(context, "Kayıt Yeniden Başladı", Toast.LENGTH_SHORT).show();
                        mediaRecorder.resume();
                        isPaused = false;
                        setPauseTag();
                        isRecording = true;

                    }
                    // kayıt sıfırdan başlıyor
                    else {
                        chronometer.setBase(SystemClock.elapsedRealtime());
                        chronometer.start();
                        setPauseTag();
                        isRecording = true;
                        isTimeCreated = true;
//                        Toast.makeText(context, imageViewRecord.getTag().toString(), Toast.LENGTH_SHORT).show();
                        prepareRecordAudio();
                    }

                }
                // kayıt durduruldu
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && imageViewRecord.getTag().equals("pause")) {
                    if (isRecording) {
                        // pausing record action
                        isRecording = false;
                        pauseRecording();
                        isPaused = true;
                    }
                    setMicTag();
//                    Toast.makeText(context, imageViewRecord.getTag().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void imgViewSaveListener() {
        imageViewSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimeCreated) {
                    // saving audio
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    chronometer.stop();
                    timeWhenStopped = 0;

                    Toast.makeText(context, String.valueOf(SystemClock.elapsedRealtime()), Toast.LENGTH_SHORT).show();

                    selectedLesson = spinner.getSelectedItem().toString().toLowerCase();
                    stopRecording();
                    setMicTag();
                    Toast.makeText(context, "Kayıt Kaydedildi", Toast.LENGTH_SHORT).show();
                    isRecording = false;
                    isTimeCreated = false;
                    isPaused = false;
                    imageViewSave.setVisibility(View.INVISIBLE);

                    StorageReference filePath = storageRef.child(userEmail).child(selectedLesson).child(recordFile);
                    String fileName = getActivity().getExternalFilesDir("/").getAbsolutePath() + "/" + recordFile;
                    Uri uri = Uri.fromFile(new File(fileName));
                    filePath.putFile(uri)
                            .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("userId", userId);
                                        hashMap.put("userEmail", userEmail);
                                        hashMap.put("voicerId", firebaseUser.getUid());
                                        hashMap.put("voicerEmail", firebaseUser.getEmail());
                                        hashMap.put("uri", uri.toString());
                                        Toast.makeText(context, uri.toString(), Toast.LENGTH_SHORT).show();
                                        String uuid = UUID.randomUUID().toString();
                                        Toast.makeText(context, "Kayıt Yüklendi", Toast.LENGTH_SHORT).show();

                                        // ses link kullanıcıya yüklendi
                                        firestore.collection("users").document(userId).collection("lessons")
                                                .document(selectedLesson)
                                                .collection("records")
                                                .document(uuid)
                                                .set(hashMap)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            HashMap<String, Object> hashMap = new HashMap<>();
                                                            hashMap.put("userId", userId);
                                                            hashMap.put("userEmail", userEmail);
                                                            hashMap.put("voicerId", firebaseUser.getUid());
                                                            hashMap.put("voicerEmail", firebaseUser.getEmail());
                                                            hashMap.put("uri", uri.toString());

                                                            // ses link voicer a yüklendi
                                                            firestore.collection("voicers").document(firebaseUser.getUid())
                                                                    .collection("records")
                                                                    .document(userId)
                                                                    .collection(selectedLesson)
                                                                    .document(uuid)
                                                                    .set(hashMap)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                Toast.makeText(context, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                                                            } else
                                                                                Toast.makeText(context, "Kayıt Yüklenmedi", Toast.LENGTH_SHORT).show();
                                                                        }

                                                                    });
                                                        }
                                                    }
                                                });
                                    }
                                }

                            });
                }
            }
        });
    }


    public void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;

        }
    }

    public void pauseRecording() {

        chronometer.stop();
        if (mediaRecorder != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mediaRecorder.pause();
                timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
                chronometer.stop();
                Toast.makeText(context, "Kayıt Durduruldu", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void prepareRecordAudio() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // izin verilmedi
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        } else {
            // izin verildi
            recordAudio();
        }
    }

    public void recordAudio() {
        String recordPath = getActivity().getExternalFilesDir("/").getAbsolutePath();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM__dd_hh_mm_ss", Locale.getDefault());
        Date now = new Date();


        selectedLesson = spinner.getSelectedItem().toString();
        recordFile = userEmail + "_" + selectedLesson.toLowerCase() + "_" + simpleDateFormat.format(now) + ".3gp";
//        recordFile = userEmail + "_" + selectedLesson.toLowerCase() + "_" + simpleDateFormat.format(now) + ".mp4" ;

        imageViewSave.setVisibility(View.VISIBLE);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(recordPath + "/" + recordFile);
//        Toast.makeText(context, "Kayıt Yeri : " + recordPath + "/" + recordFile, Toast.LENGTH_LONG).show();

        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            if (mediaRecorder != null) {
                mediaRecorder.prepare();
                mediaRecorder.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setMicTag() {
        imageViewRecord.setImageResource(R.drawable.ic_baseline_mic_red_64);
        imageViewRecord.setTag("mic");
    }

    public void setPauseTag() {
        imageViewRecord.setImageResource(R.drawable.ic_baseline_pause_64);
        imageViewRecord.setTag("pause");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recordAudio();

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaRecorder = null;
        if (isRecording) {
            isRecording = false;
            isTimeCreated = false;
            isPaused = false;

            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
        }
        Toast.makeText(context, "Destroy", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaRecorder = null;

        isRecording = false;
        isTimeCreated = false;
        isPaused = false;
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        Toast.makeText(context, "onPause", Toast.LENGTH_SHORT).show();


    }
}