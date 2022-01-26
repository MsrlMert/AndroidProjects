package com.mertmsrl.mef_idp_project.Adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.HistoryFragment;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.MatchedStudentFragment;
import com.mertmsrl.mef_idp_project.Fragments.VoicerFragments.SearchStudentFragment;

public class TabsAdapter extends FragmentPagerAdapter {
    FragmentManager fragmentManager;
    Context context;

    public TabsAdapter(@NonNull FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
        this.fragmentManager = fm;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                MatchedStudentFragment matchedStudentFragment = new MatchedStudentFragment(fragmentManager,
                        context);
                return matchedStudentFragment;
            case 1:
                SearchStudentFragment searchStudentFragment = new SearchStudentFragment(context);
                return searchStudentFragment;
            case 2:
                HistoryFragment historyFragment = new HistoryFragment(fragmentManager, context);
                return historyFragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Eşleşilmiş Öğrenciler";
            case 1:
                return "Tüm Öğrenciler";
            case 2:
                return "Kayıtlar";
            default:
                return null;
        }
    }
}
