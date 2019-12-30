package com.miti.meeti.database.Feed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class FeedViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> mTodos;

    public FeedViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<String>> getTodos() {
        if (mTodos == null) {
            mTodos = new MutableLiveData<List<String>>();
            loadTodos();
        }
        return mTodos;
    }
    private void loadTodos() {
        List<String> newTodos = new ArrayList<String>();
        newTodos.add("Apoorva");
        newTodos.add("Gaurav");
        mTodos.setValue(newTodos);
    }
    public void addTodo(String name) {
        List<String> todos = mTodos.getValue();
        ArrayList<String> clonedTodos = new ArrayList<String>(todos.size());
        for(int i = 0; i < todos.size(); i++){
            clonedTodos.add(new String(todos.get(i)));
        }
        String todo = new String(name);
        clonedTodos.add(todo);
        mTodos.setValue(clonedTodos);
    }
}
