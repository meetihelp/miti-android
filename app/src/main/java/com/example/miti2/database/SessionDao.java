package com.example.miti2.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface SessionDao {
    @Query("Select miticookie from session where id=1")
    String getCookie();

    @Insert
    void InsertSession(Session session);

    @Delete
    void DeleteSession(Session session);
}
