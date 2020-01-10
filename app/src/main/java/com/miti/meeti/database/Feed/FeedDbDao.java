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

    @Query("Update Feed set Thought=:thought,Sync=0 where Id=:id")
    public void reaction(String thought,int id);

    @Query("Select * from Feed")
    public LiveData<List<FeedDb>>getall();

    @Query("Select * from Feed order by Id desc limit 1")
    public FeedDb getmax();

}
