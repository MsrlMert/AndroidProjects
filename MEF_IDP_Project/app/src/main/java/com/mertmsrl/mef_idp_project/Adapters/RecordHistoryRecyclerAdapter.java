package com.mertmsrl.mef_idp_project.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.HistoryLessonFragment;
import com.mertmsrl.mef_idp_project.R;

import java.util.List;

public class RecordHistoryRecyclerAdapter extends RecyclerView.Adapter<RecordHistoryRecyclerAdapter.ViewHolder> {



//    HashMap<String, String> recordHistoryLessonsList;
    List<String> matchedStudentEmailList;
    List<String> matchedStudentIdList;

    Context context;
    FragmentManager fragmentManager;
    String userEmail, userId;

//    public RecordHistoryRecyclerAdapter(List<String> recordHistoryList, FragmentManager fragmentManager, Context context) {
//        this.recordHistoryLessonsList = recordHistoryList;
//        this.fragmentManager = fragmentManager;
//        this.context = context;
//    }

    public RecordHistoryRecyclerAdapter(List<String> matchedStudentEmailList, List<String> matchedStudentIdList,
                                        FragmentManager fragmentManager, Context context) {
        this.matchedStudentEmailList = matchedStudentEmailList;
        this.matchedStudentIdList = matchedStudentIdList;
        this.fragmentManager = fragmentManager;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_history_recycler_view_past_records_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        lessonName = recordHistoryLessonsList.get(position);
        userId = matchedStudentIdList.get(position);
        userEmail = matchedStudentEmailList.get(position);

        holder.textView.setText(userEmail);
        setImage(holder.imageView);

        Toast.makeText(context, "Id : "+userId, Toast.LENGTH_SHORT).show();
        Toast.makeText(context, "Email: "+userEmail , Toast.LENGTH_SHORT).show();


        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // open history lesson fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HistoryLessonFragment historyLessonFragment = new HistoryLessonFragment(context, userEmail,
                        userId);
                fragmentTransaction.replace(R.id.content_main_voicer_frame_layout, historyLessonFragment);
                fragmentTransaction.commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return matchedStudentEmailList.size();
    }

    public void setImage(ImageView imageView){
        String lessonNameLower = userEmail.toLowerCase();
        if (lessonNameLower.equals("türkce")){
            imageView.setImageResource(R.drawable.ic_launcher_background);

        }else if (lessonNameLower.equals("matematik")){

        }else if (lessonNameLower.equals("fizik")){

        }else if (lessonNameLower.equals("kimya")){

        }else if (lessonNameLower.equals("ingilizce")){

        }else if (lessonNameLower.equals("tarih")){

        }else if (lessonNameLower.equals("cografya")){

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.fragment_history_recycler_view_row_relative_layout);
            imageView = itemView.findViewById(R.id.fragment_history_recycler_view_past_records_row_img_view_lesson);
            textView = itemView.findViewById(R.id.fragment_history_recycler_view_past_records_row_text_user_email);

        }
    }
}
