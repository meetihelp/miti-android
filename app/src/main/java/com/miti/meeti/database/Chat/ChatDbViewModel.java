package com.miti.meeti.database.Chat;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ChatDbViewModel extends AndroidViewModel {
    private ChatDbRepository repository;
    private LiveData<List<ChatDb>> chats;
    public ChatDbViewModel(@NonNull Application application) {
        super(application);
        repository = new ChatDbRepository(application);
    }
    public LiveData<List<ChatDb>>getchatbyid(String chatid){
        this.chats=repository.getchatbyid(chatid);
        return chats;
    }
    public void insert(ChatDb ...chats){
        repository.insert(chats);
    }
    public void insertnew(ChatDb ...chats){
        repository.insertnew(chats);
    }
    public String getmax(String ...chatid){
        return repository.getmax(chatid[0]);
    }
    public void delete(String ...chatid){
        repository.delete(chatid[0]);
    }
    public void synced(String ...chatid){
        repository.Synced(chatid);
    }
    public void getnotlive(String chatid){repository.getnotlive(chatid);}
    public void ifrow(String chatid){repository.ifrow(chatid);}
}
