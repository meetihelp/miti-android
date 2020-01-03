package com.miti.meeti.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.miti.meeti.database.Cookie.Cookie;
import com.miti.meeti.database.Cookie.CookieDao;

@Database(entities = {Cookie.class}, version = 1,exportSchema = false)
public abstract class DatabaseInit extends RoomDatabase {
    private static DatabaseInit instance;
    public abstract CookieDao cookieDao();
    public static synchronized DatabaseInit getInstance(Context context) {
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), DatabaseInit.class, "user-database")
                    // allow queries on the main thread.
                    // Don't do this on a real app! See PersistenceBasicSample for an example.
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }
}

