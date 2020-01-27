package com.miti.meeti.database.Diary;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Moodboard",indices = {@Index(value = "created_at"),@Index(value = "user_created_At"),@Index(value = "request_id"),@Index(value = "miti_sync")})
public class Moodboard {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String Content;
    public String BoardId;
    public String Contentid;
    public String Mimetype;
    @ColumnInfo(name = "user_created_At")
    public String UserCreatedAt;
    @ColumnInfo(name = "created_at")
    public String CreatedAt;
    @ColumnInfo(name = "request_id")
    public String RequestId;
    public String Tag;
    public String ImagePath;
    public String ImageId;
    @ColumnInfo(name = "miti_sync")
    public int Sync;
    public Moodboard(){}
    public Moodboard(String BoardId,String requestId,String userCreatedAt,String mimetype,String content,String image_path,int sync){
        this.BoardId=BoardId;
        this.RequestId=requestId;
        this.UserCreatedAt=userCreatedAt;
        this.Mimetype=mimetype;
        this.Content=content;
        this.ImagePath=image_path;
        this.Sync=sync;
    }
}
