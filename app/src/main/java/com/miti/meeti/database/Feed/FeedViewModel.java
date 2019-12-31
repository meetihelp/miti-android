package com.miti.meeti.database.Feed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.ui.newsfeed.FeedRequest;

import java.util.ArrayList;
import java.util.List;

public class FeedViewModel extends AndroidViewModel {
    private MutableLiveData<List<String>> mTodos;
    public static int templkh=-1;
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
        List<Feed.feed_object>lkj= FeedRequest.getinitialnews();
        if(lkj.size()==0){
        }else{
            newTodos.add(lkj.get(0).Summary);
            newTodos.add(lkj.get(1).Summary);
            templkh=lkj.get(1).Id+1;
        }
        mTodos.setValue(newTodos);
    }
    public void addTodo(String name) {
//        String name=FeedRequest.getlaternews();
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
