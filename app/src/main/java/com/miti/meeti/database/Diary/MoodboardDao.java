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

    @Query("Select * from Moodboard order by user_created_At desc")
    public LiveData<List<Moodboard>>getall();

    @Query("Select * from Moodboard where miti_sync=-1 order by user_created_At asc")
    public List<Moodboard>getallnotsynced();
    @Query("Update Moodboard set miti_sync=1,Contentid=:contentid,created_At=:createdAt where request_id=:requestId")
    public void update(String requestId,String contentid,String createdAt);

    @Query("Update Moodboard set miti_sync=1,ImageId=:imageId where request_id=:requestId")
    public void updateimage(String requestId,String imageId);

    @Query("Update Moodboard set Content = :content where request_id=:requestId")
    public void updateContent(String content,String requestId);

    @Delete
    public void delete(Moodboard ...moodboards);

    @Query("Select * from Moodboard order by created_At desc limit 1")
    public Moodboard getmax();
}
