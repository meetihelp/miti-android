package com.miti.meeti.database.Chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ChatListDbViewModel extends AndroidViewModel {
    private ChatListDbRepository repository;
    private LiveData<ChatListDb[]> chatlist;
    public ChatListDbViewModel(@NonNull Application application) {
        super(application);
        repository = new ChatListDbRepository(application);
    }
    public LiveData<ChatListDb[]>getchatbyid(String chatid){
        this.chatlist=repository.getall();
        return chatlist;
    }
    public void insert(ChatListDb ...chatlist){
        repository.insert(chatlist);
    }
    public void delete(String ...chatid){
        repository.delete(chatid[0]);
    }
    public void synced(String ...chatid){
        repository.Synced(chatid[0]);
    }
}