package com.miti.meeti.database.Chat;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.ui.newsfeed.FeedRequest;
import com.miti.meeti.ui.social.chat.ChatListRequest;

import java.util.ArrayList;
import java.util.List;

public class ChatListViewModel extends AndroidViewModel {
//    private  MutableLiveData<List<ChatList.chatlist_object>> mTodos;
    public ChatListViewModel(@NonNull Application application) {
        super(application);
    }
//    public LiveData<List<ChatList.chatlist_object>> getTodos() {
//        if (mTodos == null) {
//            mTodos = new MutableLiveData<List<ChatList.chatlist_object>>();
//        }
//        return mTodos;
//    }
//    public void loadTodos() {
////        List<ChatList.chatlist_object> newTodos = new ArrayList<>();
//        List<ChatList.chatlist_object>lkj= ChatListRequest.getinitialnews();
//        mTodos.setValue(lkj);
//    }
//    public void setTodos(List<ChatList.chatlist_object> newTodos) {
////        List<ChatList.chatlist_object> newTodos = new ArrayList<>();
//        mTodos.setValue(newTodos);
//    }
//    public void addTodo(List<ChatList.chatlist_object>newtodos) {
////        String name=FeedRequest.getlaternews();
//        List<ChatList.chatlist_object> todos = mTodos.getValue();
//        ArrayList<ChatList.chatlist_object> clonedTodos = new ArrayList<>();
//        try{
//            for(int i = 0; i < todos.size(); i++){
//                clonedTodos.add(todos.get(i));
//            }
//        }catch (Exception e){
//        }
//        for (ChatList.chatlist_object chat_list_helper:newtodos){
//            clonedTodos.add(chat_list_helper);
//        }
//        mTodos.setValue(clonedTodos);
//    }
}
