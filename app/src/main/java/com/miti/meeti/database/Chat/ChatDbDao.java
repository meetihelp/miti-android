package com.miti.meeti.database.Chat;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChatDbDao {
    @Query("SELECT * FROM MeetiChat where ChatId = :chatId and Sync!=-3 order by CreatedAt desc limit 100")
    LiveData<List<ChatDb>> getchatbyid(String chatId);
    @Query("SELECT * FROM MeetiChat where ChatId = :chatId order by CreatedAt desc limit 100")
    List<ChatDb> getchatnotlive(String chatId);
    @Query("SELECT * FROM MeetiChat where Sync = -1")
    List<ChatDb> getchatnotsynced();

    @Query("SELECT * FROM MeetiChat where Sync = -3")
    List<ChatDb> getnotsyncedimage();
    @Query("Update MeetiChat set Sync=1,ImageUrl =:imageUrl where MessageId = :MessageId")
    void updatesyncimage(String MessageId,String imageUrl);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertChat(ChatDb ...chat);

    @Query("Delete FROM MeetiChat where ChatId = :chatId")
    void deleteChat(String chatId);

    @Query("Update MeetiChat set Sync=1,MessageId =:messageid,CreatedAt=:createdAt where RequestId = :requestid")
    void Synced(String requestid,String messageid,String createdAt);
    @Query("Update MeetiChat set Sync=1,MessageId =:messageid,MessageContent=:imageid,CreatedAt=:createdAt where RequestId = :requestid")
    void SyncedImage(String requestid,String messageid, String imageid,String createdAt);
    @Query("Select * from MeetiChat where ChatId = :chatId order by CreatedAt desc limit 1")
    ChatDb getmax(String chatId);

    @Query("Select * from MeetiChat where ChatId = :chatId limit 1")
    ChatDb ifrow(String chatId);
}
