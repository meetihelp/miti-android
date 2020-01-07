package com.miti.meeti.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Chat.ChatDbDao;
import com.miti.meeti.database.Chat.ChatListDb;
import com.miti.meeti.database.Chat.ChatListDbDao;
import com.miti.meeti.database.Cookie.Cookie;
import com.miti.meeti.database.Cookie.CookieDao;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.database.Keyvalue.KeyvalueDao;

@Database(entities = {Cookie.class, ChatDb.class, ChatListDb.class, keyvalue.class}, version = 1,exportSchema = false)
public abstract class DatabaseInit extends RoomDatabase {
    private static DatabaseInit instance;
    public abstract CookieDao cookieDao();
    public abstract ChatDbDao chatDbDao();
    public abstract ChatListDbDao chatListDbDao();
    public abstract KeyvalueDao keyvalueDao();
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

