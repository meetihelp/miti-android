package com.miti.meeti.database.Chat;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MeetiChatList")
public class ChatListDb {
    public String UserId;
    @PrimaryKey
    @NonNull
    public String ChatId;
    public String ChatType;
    public String CreatedAt;
    public String LastUpdate;
    public String ImageURL;
    public String Name;
    public String UserId2;
    public String Gender;
    public String LastMessage;
    public int Sync;
}
