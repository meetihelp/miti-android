package com.miti.meeti.database.Feed;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FeedDbDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(FeedDb ...feedDbs);

    @Query("Update Feed set Thought=:thought,Sync=0 where miti_id=:id")
    public void reaction(String thought,int id);

    @Query("Select * from Feed order by user_Created_at desc")
    public LiveData<List<FeedDb>>getall();

    @Query("Select * from Feed order by miti_id desc limit 1")
    public FeedDb getmax();

}
