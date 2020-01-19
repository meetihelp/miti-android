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
    @Query("Update Moodboard set sync=1,Contentid=:contentid,CreatedAt=:createdAt where RequestId=:requestId")
    public void update(String requestId,String contentid,String createdAt);

    @Query("Update Moodboard set sync=1,ImageId=:imageId where RequestId=:requestId")
    public void updateimage(String requestId,String imageId);

    @Query("Update Moodboard set Content = :content where RequestId=:requestId")
    public void updateContent(String content,String requestId);

    @Delete
    public void delete(Moodboard ...moodboards);

    @Query("Select * from Moodboard order by CreatedAt desc limit 1")
    public Moodboard getmax();
}
