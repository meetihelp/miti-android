package com.miti.meeti.database.Chat;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "MeetiChatList",indices = {@Index(value = {"created_at"}),@Index(value = {"miti_sync"})})
public class ChatListDb {
    public String UserId;
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "chat_id")
    public String ChatId;
    public String ChatType;
    @ColumnInfo(name = "created_at")
    public String CreatedAt;
    public String LastUpdate;
    public String ImageURL;
    public String Name;
    public String UserId2;
    public String Gender;
    public String LastMessage;
    @ColumnInfo(name = "miti_sync")
    public int Sync;
}
