package com.miti.meeti.database.Diary;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Moodboard")
public class Moodboard {
    @PrimaryKey(autoGenerate = true)
    public int uid;
    public String Content;
    public String Mimetype;
    public String UserCreatedAt;
    public String CreatedAt;
    public String RequestId;
    public String Tag;
    public String ImagePath;
    public int Sync;
    public Moodboard(){}
    public Moodboard(String requestId,String userCreatedAt,String mimetype,String content,String image_path){
        this.RequestId=requestId;
        this.UserCreatedAt=userCreatedAt;
        this.Mimetype=mimetype;
        this.Content=content;
        this.ImagePath=image_path;
    }
}
