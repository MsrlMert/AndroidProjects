package com.mertmsrl.mef_idp_project.ModelViews.UserModelViews.SavedModelView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mertmsrl.mef_idp_project.Models.Book;

import java.util.List;

public class SavedModelView extends ViewModel {

    MutableLiveData<List<Book>> bookListMutableLiveData;

    public LiveData<List<Book>> getSaved(){
        if (bookListMutableLiveData == null){
            bookListMutableLiveData = new MutableLiveData<>();
            loadSaved();
        }
        return bookListMutableLiveData;
    }

    private void loadSaved() {

    }
}
