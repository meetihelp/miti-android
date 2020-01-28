package com.miti.meeti.database.Feed;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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
    @Query("SELECT COUNT(*) FROM Feed")
    public Integer getCount();
    @Query("delete from Feed where miti_id IN (SELECT miti_id from Feed order by user_created_at asc limit :lim)")
    public void deletelastx(int lim);
    @Query("Select * from Feed order by user_created_at desc")
    public LiveData<List<FeedDb>>getall();
    @Delete
    public void delete(FeedDb ...feedDb);
    @Query("Select * from Feed order by miti_id desc limit 1")
    public FeedDb getmax();

}
