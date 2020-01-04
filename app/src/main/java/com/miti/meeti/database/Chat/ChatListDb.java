package com.miti.meeti.database.Chat;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MeetiChatList")
public class ChatListDb {
    public String TempUserId;
    @PrimaryKey
    @NonNull
    public String ChatId;
    public String ChatType;
    public String CreatedAt;
    public String LastUpdate;
    public int Sync;
}