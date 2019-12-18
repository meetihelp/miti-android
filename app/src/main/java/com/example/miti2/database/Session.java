package com.example.miti2.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "session")
public class Session {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name="miticookie")
    public String MitiCookie;

    public Session(String MitiCookie){
        this.MitiCookie=MitiCookie;
    }

}