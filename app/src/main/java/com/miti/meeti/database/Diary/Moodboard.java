package com.miti.meeti.database.Diary;

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
    public int Sync;
}
