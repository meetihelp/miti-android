package com.miti.meeti.database.Diary;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MoodboardViewModel extends AndroidViewModel {
    private MoodboardRepository moodboardRepository;
    private LiveData<List<Moodboard>> all;
    public MoodboardViewModel(@NonNull Application application) {
        super(application);
        moodboardRepository=new MoodboardRepository(application);
        all=moodboardRepository.getall();
    }

    public LiveData<List<Moodboard>> getAll() {
        return all;
    }
    public List<Moodboard> getallnotsynced() {
        return moodboardRepository.getallnotsynced();
    }

    public void insert(Moodboard ...moodboards){
        moodboardRepository.insert(moodboards);
    }
    public void delete(Moodboard ...moodboards){
        moodboardRepository.delete(moodboards);
    }
    public void update(String ...temp){
        moodboardRepository.update(temp);
    }
    public void updateimage(String ...temp){
        moodboardRepository.updateimage(temp);
    }
    public void updateContent(String ...temp){
        moodboardRepository.updateContent(temp);
    }
    public Moodboard getmax(){return moodboardRepository.getmax();}
}
