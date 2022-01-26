package com.mertmsrl.mef_idp_project.Fragments.VoicerFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mertmsrl.mef_idp_project.Adapters.RecordHistoryRecyclerAdapter;
import com.mertmsrl.mef_idp_project.ModelViews.VoicerModelViews.RecordHistoryMatchedStudentsModelView;
import com.mertmsrl.mef_idp_project.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HistoryFragment extends Fragment {


    RecyclerView recyclerView;
    //LinearLayoutManager layoutManager;
    GridLayoutManager gridLayoutManager;
    FragmentManager fragmentManager;
    Context context;
    RecordHistoryMatchedStudentsModelView recordHistoryMatchedStudentsModelView;
    HashMap<String, String> matchedStudentsHashMap = new HashMap<>();
    List<String> matchedStudentEmailList = new ArrayList<>();
    List<String> matchedStudentIdList = new ArrayList<>();




    public HistoryFragment(FragmentManager fragmentManager, Context context) {
        this.fragmentManager = fragmentManager;
        this.context = context;

        //layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        gridLayoutManager = new GridLayoutManager(context, 3);
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.fragment_history_recycler_view_past_records);

        recordHistoryMatchedStudentsModelView = new ViewModelProvider(this)
                .get(RecordHistoryMatchedStudentsModelView.class);




        recordHistoryMatchedStudentsModelView.getRecordHistory().observe(getViewLifecycleOwner(), stringStringHashMap -> {
            matchedStudentsHashMap = stringStringHashMap;
            matchedStudentEmailList = new ArrayList<>();
            for (String key : matchedStudentsHashMap.keySet()){
                matchedStudentIdList.add(key);
                matchedStudentEmailList.add(matchedStudentsHashMap.get(key));
            }

            RecordHistoryRecyclerAdapter recyclerAdapter = new RecordHistoryRecyclerAdapter(matchedStudentEmailList,
                    matchedStudentIdList, fragmentManager, context);
            recyclerView.setAdapter(recyclerAdapter);

        });

        gridLayoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(gridLayoutManager);




        return view;
    }
}