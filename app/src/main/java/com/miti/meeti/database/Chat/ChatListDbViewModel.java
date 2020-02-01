package com.miti.meeti.database.Chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ChatListDbViewModel extends AndroidViewModel {
    private ChatListDbRepository repository;
    private LiveData<List<ChatListDb>> chatlist;
    public ChatListDbViewModel(@NonNull Application application) {
        super(application);
        repository = new ChatListDbRepository(application);
        chatlist=repository.getall();
    }
    public LiveData<List<ChatListDb>> getall(){
        return chatlist;
    }
    public ChatListDb getmax(){
        return repository.getmax();
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
    public List<ChatListDb> getold(){return repository.getallold();}
    public void updatelast(String chatid, String lastupdate,String lastmessage){
        repository.updatelast(chatid,lastupdate,lastmessage);
    }
}