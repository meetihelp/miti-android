package com.miti.meeti.database.Feed;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miti.meeti.NetworkObjects.Feed;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedViewModel extends AndroidViewModel {
    private FeedDbRepository feedDbRepository;
    private LiveData<List<FeedDb>> all;
    public FeedViewModel(@NonNull Application application) {
        super(application);
        feedDbRepository=new FeedDbRepository(application);
        all=feedDbRepository.getall();
    }
    public LiveData<List<FeedDb>> getAll() {
        return all;
    }

    public void insert(FeedDb ...feeds){
        feedDbRepository.insert(feeds);
    }
    public void delete(FeedDb ...feeds){
        feedDbRepository.delete(feeds);
    }
    public int getmax(){
        Integer temp=feedDbRepository.getmax();
        if(temp==null){
            return 0;
        }else{
            return temp.intValue();
        }
    }
    public void react(String thought, String id){
        feedDbRepository.react(thought,id);
    }
}

//    private MutableLiveData<List<Feed.feed_object>> mTodos;
//    public static int templkh=-1;
//    private static Map<String, Integer> myMap=new HashMap<>();
//    public FeedViewModel(@NonNull Application application) {
//        super(application);
//    }
//    public LiveData<List<Feed.feed_object>> getTodos() {
//        if (mTodos == null) {
//            mTodos = FeedDbRepository;
//          loadTodos();
//        }
//        return mTodos;
//    }
//    private void loadTodos() {
//        List<Feed.feed_object> newTodos = new ArrayList<>();
//        List<Feed.feed_object>lkj= new ArrayList<>();
//        if(lkj.size()==0){
//        }else{
//            newTodos.add(lkj.get(0));
//            newTodos.add(lkj.get(1));
//            templkh=lkj.get(1).Id+1;
//
//        }
//        mTodos.setValue(newTodos);
//    }
//    public void addTodo(List<Feed.feed_object>newtodo) {
////        String name=FeedRequest.getlaternews();
//        List<Feed.feed_object> todos = mTodos.getValue();
//        ArrayList<Feed.feed_object> clonedTodos = new ArrayList<>();
//        for(int i = 0; i < todos.size(); i++){
//            clonedTodos.add(todos.get(i));
//        }
//        for(Feed.feed_object tempxd:newtodo){
//            clonedTodos.add(tempxd);
//        }
//        mTodos.setValue(clonedTodos);
//    }