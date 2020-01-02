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
    private MutableLiveData<List<Feed.feed_object>> mTodos;
    public static int templkh=-1;
    public FeedViewModel(@NonNull Application application) {
        super(application);
    }
    public LiveData<List<Feed.feed_object>> getTodos() {
        if (mTodos == null) {
            mTodos = new MutableLiveData<List<Feed.feed_object>>();
            loadTodos();
        }
        return mTodos;
    }
    private void loadTodos() {
        List<Feed.feed_object> newTodos = new ArrayList<>();
        List<Feed.feed_object>lkj= FeedRequest.getinitialnews();
        if(lkj.size()==0){
        }else{
            newTodos.add(lkj.get(0));
            newTodos.add(lkj.get(1));
            templkh=lkj.get(1).Id+1;
        }
        mTodos.setValue(newTodos);
    }
    public void addTodo(List<Feed.feed_object>newtodo) {
//        String name=FeedRequest.getlaternews();
        List<Feed.feed_object> todos = mTodos.getValue();
        ArrayList<Feed.feed_object> clonedTodos = new ArrayList<>();
        for(int i = 0; i < todos.size(); i++){
            clonedTodos.add(todos.get(i));
        }
        for(Feed.feed_object tempxd:newtodo){
            clonedTodos.add(tempxd);
        }
        mTodos.setValue(clonedTodos);
    }
}
