package com.miti.meeti.database.Cookie;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CookieDao {
    @Query("SELECT meeti_cookie FROM MeetiCookie")
    LiveData<String[]>getCookie();

    @Query("SELECT meeti_cookie FROM MeetiCookie limit 1")
    String getCookie1();

    @Insert
    void insertCookie(Cookie... cookies);

    @Delete
    void deleteCookie(Cookie cookie);

    @Query("DELETE FROM MeetiCookie")
    void nukeTable();
}
