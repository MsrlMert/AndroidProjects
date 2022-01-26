package com.mertmsrl.mef_idp_project.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mertmsrl.mef_idp_project.R;

import java.util.List;

public class HistoryLessonFragmentRecyclerAdapter extends RecyclerView.Adapter<HistoryLessonFragmentRecyclerAdapter.ViewHolder> {

    List<String> stringLessonList;
    Context context;

    public HistoryLessonFragmentRecyclerAdapter(List<String> stringLessonList, Context context) {
        this.stringLessonList = stringLessonList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_lesson_fragment_recycler_view_row, parent
        ,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String lessonName = stringLessonList.get(position);

        holder.textViewLessonName.setText(lessonName);
    }

    @Override
    public int getItemCount() {
        return stringLessonList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLessonName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewLessonName = itemView.findViewById(R.id.history_lesson_fragment_recycler_view_row_text_lesson_name);

        }
    }
}
