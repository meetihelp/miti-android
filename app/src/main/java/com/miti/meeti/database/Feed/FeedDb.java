package com.miti.meeti.database.Feed;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Feed",indices = {@Index(value = "user_created_at"),@Index(value = "miti_id")})
public class FeedDb {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "miti_id")
    public int Id;
    public String Summary;
    @ColumnInfo(name="user_created_at")
    public String UserCreatedAt;
    public String Sentiment;
    public String Location;
    public String Event;
    public String Label;
    public String Title;
    public String ImageURL;
    public String ArticeURL;
    public String Flag;
    public String Tag;
    public String Thought;
    public int Sync;
}
