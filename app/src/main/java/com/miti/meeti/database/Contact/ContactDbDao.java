package com.miti.meeti.database.Contact;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.miti.meeti.database.Cookie.Cookie;

import java.util.List;


@Dao
public interface ContactDbDao {
    @Query("SELECT * FROM ContactDb where Tag!='deleted'")
    LiveData<List<ContactDb>> getall();

    @Query("SELECT * FROM ContactDb where Status=-1")
    List<ContactDb> getallcontact();
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ContactDb... contactDbs);

    @Delete
    void delete(ContactDb... contactDbs);

    @Query("Update ContactDb set Status=:status where uid=:uid")
    void update(int status,int uid);
}