package com.mertmsrl.mef_idp_project.Fragments.VoicerFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mertmsrl.mef_idp_project.Adapters.MatchedStudentRecyclerAdapter;
import com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews.MatchedStudentModelView;
import com.mertmsrl.mef_idp_project.Models.User;
import com.mertmsrl.mef_idp_project.R;

import java.util.List;


public class MatchedStudentFragment extends Fragment {


    RecyclerView recyclerViewMatchedStudents;
    Context context;
    //RecyclerView.LayoutManager layoutManager;
    MatchedStudentModelView matchedStudentModelView;
    TextView textViewNoStudent;
    FragmentManager fragmentManager;


    public MatchedStudentFragment(FragmentManager fragmentManager, Context context) {
        this.context = context;
        //layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_matched_student, container, false);

        recyclerViewMatchedStudents = view.findViewById(R.id.fragment_matched_student_recycler_view);
        textViewNoStudent = view.findViewById(R.id.fragment_matched_student_text_view_no_student);
        //recyclerViewMatchedStudents.setLayoutManager(layoutManager);
        recyclerViewMatchedStudents.setLayoutManager(new LinearLayoutManager(getActivity()));
        matchedStudentModelView = new ViewModelProvider(this)
                .get(MatchedStudentModelView.class);


        matchedStudentModelView.getUsers().observe(getViewLifecycleOwner(), userList -> {
            if (userList.size() > 0) {
                textViewNoStudent.setVisibility(View.INVISIBLE);
            } else

            textViewNoStudent.setVisibility(View.VISIBLE);
            MatchedStudentRecyclerAdapter studentRecyclerAdapter = new MatchedStudentRecyclerAdapter(fragmentManager, userList, context);
            recyclerViewMatchedStudents.setAdapter(studentRecyclerAdapter);

        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}