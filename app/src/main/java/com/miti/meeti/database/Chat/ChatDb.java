package com.miti.meeti.database.Chat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "MeetiChat", indices = {@Index(value = {"chat_id"}),@Index(value = {"created_at"}),@Index(value = {"miti_sync"})})
public class ChatDb {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String UserId;
    @ColumnInfo(name = "chat_id")
    public String ChatId;
    public String MessageId;
    public String MessageType;
    public String MessageContent;
    public String ImageId;
    public String ImageUrl;
    public String UserCreatedAt;
    @ColumnInfo(name = "created_at")
    public String CreatedAt;
    public String RequestId;
    public int Read;
    @ColumnInfo(name="miti_sync")
    public int Sync;
    public ChatDb(){

    }
    public ChatDb(String userId,String messageType,String messageContent, String requestId, String chatId
    ,String usercreatedAt, int sync){
        this.UserId=userId;
        this.MessageType=messageType;
        this.MessageContent=messageContent;
        this.RequestId=requestId;
        this.ChatId=chatId;
        this.UserCreatedAt=usercreatedAt;
        this.Sync=sync;
    }
    public ChatDb(String userId,String chatId,String messageType,String imageUrl,String requestId,int sync, String userCreatedAt){
        this.UserId=userId;
        this.MessageType=messageType;
        this.ImageUrl=imageUrl;
        this.RequestId=requestId;
        this.ChatId=chatId;
        this.Sync=sync;
        this.UserCreatedAt=userCreatedAt;
    }
}
