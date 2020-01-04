package com.miti.meeti.database.Chat;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDbDao {
    @Query("SELECT * FROM MeetiChat where ChatId = :chatId order by CreatedAt desc limit 100")
    LiveData<List<ChatDb>> getchatbyid(String chatId);

    @Insert
    void insertChat(ChatDb ...chat);

    @Query("Delete FROM MeetiChat where ChatId = :chatId")
    void deleteChat(String chatId);

    @Query("Update MeetiChat set Sync=1 where ChatId = :chatId")
    void Synced(String chatId);

    @Query("Select * from MeetiChat where ChatId = :chatId order by Indexz desc limit 1")
    ChatDb getmax(String chatId);
}
