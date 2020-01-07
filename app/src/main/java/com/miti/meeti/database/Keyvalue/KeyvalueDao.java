package com.miti.meeti.database.Keyvalue;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.mitiutil.network.Keyvalue;

import java.util.List;

@Dao
public interface KeyvalueDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(keyvalue ...temp);

    @Query("Select * from MeetiKV where mitikey=:key limit 1")
    keyvalue get(String key);

    @Query("Update MeetiKV set sync=1 where mitikey=:key")
    void synced(String key);
}
