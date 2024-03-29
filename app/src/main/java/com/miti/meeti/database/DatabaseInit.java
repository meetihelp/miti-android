package com.miti.meeti.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Chat.ChatDbDao;
import com.miti.meeti.database.Chat.ChatListDb;
import com.miti.meeti.database.Chat.ChatListDbDao;
import com.miti.meeti.database.Contact.ContactDb;
import com.miti.meeti.database.Contact.ContactDbDao;
import com.miti.meeti.database.Cookie.Cookie;
import com.miti.meeti.database.Cookie.CookieDao;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.database.Diary.MoodboardDao;
import com.miti.meeti.database.Feed.FeedDb;
import com.miti.meeti.database.Feed.FeedDbDao;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.database.Keyvalue.KeyvalueDao;
import com.miti.meeti.database.Request.MessageRq;
import com.miti.meeti.database.Request.MessageRqDao;

@Database(entities = {Cookie.class, ChatDb.class, ChatListDb.class, keyvalue.class, Moodboard.class, FeedDb.class, ContactDb.class, MessageRq.class}, version = 1,exportSchema = false)
public abstract class DatabaseInit extends RoomDatabase {
    private static DatabaseInit instance;
    public abstract CookieDao cookieDao();
    public abstract ChatDbDao chatDbDao();
    public abstract ChatListDbDao chatListDbDao();
    public abstract KeyvalueDao keyvalueDao();
    public abstract MoodboardDao moodboardDao();
    public abstract FeedDbDao feedDbDao();
    public abstract ContactDbDao contactDbDao();
    public abstract MessageRqDao messageRqDao();
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

