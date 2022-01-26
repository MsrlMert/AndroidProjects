package com.mertmsrl.mef_idp_project.Fragments.VoicerFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mertmsrl.mef_idp_project.Adapters.StudentSearchRecyclerAdapter;
import com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews.SearchStudentModelView;
import com.mertmsrl.mef_idp_project.R;


public class SearchStudentFragment extends Fragment {
    RecyclerView recyclerViewSearchStudent;
    SearchStudentModelView searchStudentModelView;
    Context context;
    //LinearLayoutManager layoutManager;


    public SearchStudentFragment(Context context) {
        this.context = context;
        //layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_search_student, container, false);

        recyclerViewSearchStudent = view.findViewById(R.id.fragment_search_student_recycler_view);
        searchStudentModelView = new ViewModelProvider(this)
                .get(SearchStudentModelView.class);

        searchStudentModelView.getUsers().observe(getViewLifecycleOwner(), userList -> {
            StudentSearchRecyclerAdapter studentSearchRecyclerAdapter = new StudentSearchRecyclerAdapter(userList, context);

            recyclerViewSearchStudent.setAdapter(studentSearchRecyclerAdapter);
        });
        //recyclerViewSearchStudent.setLayoutManager(layoutManager);
        recyclerViewSearchStudent.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}