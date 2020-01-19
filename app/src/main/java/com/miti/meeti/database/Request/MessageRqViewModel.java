package com.miti.meeti.database.Request;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MessageRqViewModel extends AndroidViewModel {
    private MessageRqRepository messageRqRepository;
    private LiveData<List<MessageRq>> all;
    public MessageRqViewModel(@NonNull Application application) {
        super(application);
        messageRqRepository=new MessageRqRepository(application);
        all=messageRqRepository.getall();
    }

    public LiveData<List<MessageRq>> getAll() {
        return all;
    }
    public List<MessageRq> getallnotsynced() {
        return messageRqRepository.getallnotsynced();
    }

    public void insert(MessageRq ...messageRqs){
        messageRqRepository.insert(messageRqs);
    }
    public void delete(MessageRq ...messageRqs){
        messageRqRepository.delete(messageRqs);
    }
    public void update(String ...temp){
        messageRqRepository.update(temp);
    }
    public void updateimage(String ...temp){
        messageRqRepository.updateimage(temp);
    }
    public MessageRq getmax(){
        return messageRqRepository.getmax();
    }
}
