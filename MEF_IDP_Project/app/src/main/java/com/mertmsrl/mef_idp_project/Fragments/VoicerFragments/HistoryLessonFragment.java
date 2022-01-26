package com.mertmsrl.mef_idp_project.Fragments.VoicerFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mertmsrl.mef_idp_project.Adapters.HistoryLessonFragmentRecyclerAdapter;
import com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews.HistoryLessonModelView;
import com.mertmsrl.mef_idp_project.R;

public class HistoryLessonFragment extends Fragment {


    HistoryLessonModelView historyLessonModelView;
    Context context;
    String userEmail, userId;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;

    public HistoryLessonFragment(Context context, String userEmail, String userId) {
        this.context = context;
        this.userEmail = userEmail;
        this.userId = userId;
        gridLayoutManager = new GridLayoutManager(context, 2);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =  inflater.inflate(R.layout.fragment_history_lesson, container, false);

        recyclerView = view.findViewById(R.id.history_lesson_fragment_recycler_view);
        historyLessonModelView = new ViewModelProvider(this)
                .get(HistoryLessonModelView.class);



        historyLessonModelView.getLessons(userId).observe(getViewLifecycleOwner(), strings -> {
            Toast.makeText(context, String.valueOf(strings.size()), Toast.LENGTH_SHORT).show();
            HistoryLessonFragmentRecyclerAdapter recyclerAdapter = new HistoryLessonFragmentRecyclerAdapter(strings,
                    context);
            recyclerView.setAdapter(recyclerAdapter);
        });
        recyclerView.setLayoutManager(gridLayoutManager);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}