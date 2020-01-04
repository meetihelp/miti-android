package com.miti.meeti.database.Chat;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
@Dao
public interface ChatListDbDao {
    @Query("Select * from MeetiChatList order by LastUpdate desc")
    public LiveData<ChatListDb[]> getall();

    @Insert
    public void insert(ChatListDb...chatlist);

    @Query("Delete FROM MeetiChatList where ChatId = :chatId")
    void deleteChat(String chatId);

    @Query("Update MeetiChatList set Sync=1 where ChatId = :chatId")
    void Synced(String chatId);
}
