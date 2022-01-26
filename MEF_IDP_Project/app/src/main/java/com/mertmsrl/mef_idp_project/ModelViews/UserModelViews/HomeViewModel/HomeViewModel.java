package com.mertmsrl.mef_idp_project.ModelViews.UserModelViews.HomeViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mertmsrl.mef_idp_project.Models.Book;

import java.util.List;

public class HomeViewModel extends ViewModel {

    MutableLiveData<List<Book>> bookListMutableLiveData;

    public LiveData<List<Book>> getBooks(){
        if (bookListMutableLiveData == null){
            bookListMutableLiveData = new MutableLiveData<>();
            loadBooks();
        }
        return bookListMutableLiveData;
    }

    private void loadBooks() {

    }
}
