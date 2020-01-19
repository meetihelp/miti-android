package com.miti.meeti.database.Feed;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Feed")
public class FeedDb {
    @PrimaryKey
    @NonNull
    public int Id;
    public String Summary;
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
