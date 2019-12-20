package com.miti.meeti.database.Cookie;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Cookie.class}, version = 1,exportSchema = false)
public abstract class CookieDatabase extends RoomDatabase {
    private static CookieDatabase instance;
    public abstract CookieDao cookieDao();

    public static synchronized CookieDatabase getInstance(Context context) {
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), CookieDatabase.class, "user-database")
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
