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

    @Query("Select * from Moodboard where Sync=-1 order by UserCreatedAt asc")
    public List<Moodboard>getallnotsynced();
    @Query("Update Moodboard set sync=1,Contentid=:contentid where RequestId=:requestId")
    public void update(String requestId,String contentid);

    @Query("Update Moodboard set sync=1,ImageId=:imageId,Contentid=:contentid where RequestId=:requestId")
    public void updateimage(String requestId,String imageId,String contentid);
    @Query("Update Moodboard set Content = :content where RequestId=:requestId")
    public void updateContent(String content,String requestId);

    @Delete
    public void delete(Moodboard ...moodboards);
}
