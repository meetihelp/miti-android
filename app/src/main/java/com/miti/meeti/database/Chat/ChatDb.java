package com.miti.meeti.database.Chat;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "MeetiChat")
public class ChatDb {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String UserId;
    public String ChatId;
    public String MessageId;
    public String MessageType;
    public String MessageContent;
    public String CreatedAt;
    @SerializedName("Index") public int Indexz;
    public int Sync;
}
