package com.example.miti2.database.Cookie;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MeetiCookie")
public class Cookie {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "meeti_cookie")
    public String MeetiCookie;


    public void setMeetiCookie(String MeetiCookie) {
        this.MeetiCookie = MeetiCookie;
    }
}
