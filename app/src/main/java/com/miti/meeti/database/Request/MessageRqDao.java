package com.miti.meeti.database.Request;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageRqDao {
    @Insert
    public void insert(MessageRq ...temp);

    @Query("Select * from MessageRq order by UserCreatedAt desc")
    public LiveData<List<MessageRq>> getall();
    @Query("Select * from MessageRq where Sync=-1 order by UserCreatedAt asc")
    public List<MessageRq>getallnotsynced();
    @Query("Update MessageRq set Sync=1,CreatedAt=:createdAt where RequestId=:requestId")
    public void update(String requestId,String createdAt);
    @Query("Update MessageRq set Sync=1,ImageId=:imageId where RequestId=:requestId")
    public void updateimage(String requestId,String imageId);
    @Delete
    public void delete(MessageRq ...moodboards);

    @Query("Select * from MessageRq where Tag='received' order by CreatedAt limit 1")
    public MessageRq getmax();
}
