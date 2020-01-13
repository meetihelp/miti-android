package com.miti.meeti.database.Diary;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoodboardDao {
    @Insert
    public void insert(Moodboard ...temp);

    @Query("Select * from Moodboard order by UserCreatedAt desc")
    public LiveData<List<Moodboard>>getall();

    @Query("Update Moodboard set CreatedAt = :createdAt, sync=1 where RequestId=:requestId")
    public void update(String createdAt,String requestId);

    @Query("Update Moodboard set Content = :content where RequestId=:requestId")
    public void updateContent(String content,String requestId);

    @Delete
    public void delete(Moodboard ...moodboards);
}
