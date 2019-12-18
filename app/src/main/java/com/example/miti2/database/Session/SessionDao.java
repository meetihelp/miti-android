package com.example.miti2.database.Session;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.miti2.database.Session.Session;

import java.util.List;
//
//@Dao
//public interface SessionDao {
//    @Query("Select miticookie from session where id=1")
//    String getCookie();
//
//    @Insert
//    void InsertSession(Session session);
//
//    @Delete
//    void DeleteSession(Session session);
//}

@Dao
public interface SessionDao {
    @Query("SELECT * FROM sessionapoorva")
    List<Session> getAll();

    @Query("SELECT * FROM sessionapoorva WHERE uid IN (:userIds)")
    List<Session> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM sessionapoorva WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    Session findByName(String first, String last);

    @Insert
    void insertAll(Session... users);

    @Delete
    void delete(Session user);
}