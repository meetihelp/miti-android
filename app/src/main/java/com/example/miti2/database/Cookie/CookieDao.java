package com.example.miti2.database.Cookie;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CookieDao {
    @Query("SELECT meeti_cookie FROM MeetiCookie")
    String[] getCookie();

    @Insert
    void insertCookie(Cookie... cookies);

    @Delete
    void deleteCookie(Cookie cookie);
}
