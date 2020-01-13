package com.miti.meeti.database.Contact;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class ContactDbViewModel extends AndroidViewModel {
    private ContactDbRepository moodboardRepository;
    private LiveData<List<ContactDb>> all;
    public ContactDbViewModel(@NonNull Application application) {
        super(application);
        moodboardRepository=new ContactDbRepository(application);
        all=moodboardRepository.getall();
    }

    public LiveData<List<ContactDb>> getAll() {
        return all;
    }

    public void insert(ContactDb ...moodboards){
        moodboardRepository.insert(moodboards);
    }
    public void update(String ...temp){
        moodboardRepository.update(temp);
    }
}