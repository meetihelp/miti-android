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
    public String ImageUrl;
    public String CreatedAt;
    public String RequestId;
    public int Read;
    public int Sync;
    public ChatDb(){

    }
    public ChatDb(String userId,String messageType,String messageContent, String requestId, String chatId
    ,String createdAt){
        this.UserId=userId;
        this.MessageType=messageType;
        this.MessageContent=messageContent;
        this.RequestId=requestId;
        this.ChatId=chatId;
        this.CreatedAt=createdAt;
    }
    public ChatDb(String userId,String chatId,String messageType,String messageContent,String requestId){
        this.UserId=userId;
        this.MessageType=messageType;
        this.MessageContent=messageContent;
        this.RequestId=requestId;
        this.ChatId=chatId;
    }
}
