package com.miti.meeti.database.Chat;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatListDbDao {
    @Query("Select * from MeetiChatList order by LastUpdate desc")
    public LiveData<List<ChatListDb>> getall();

    @Query("Select * from MeetiChatList")
    public List<ChatListDb> getallnotlive();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insert(ChatListDb...chatlist);

    @Query("Delete FROM MeetiChatList where ChatId = :chatId")
    void deleteChat(String chatId);

    @Query("Update MeetiChatList set Sync=1 where ChatId = :chatId")
    void Synced(String chatId);

    @Query("Update MeetiChatList set Sync=1,LastUpdate=:lastupdate where ChatId = :chatId")
    void UpdateLast(String chatId,String lastupdate);
}
