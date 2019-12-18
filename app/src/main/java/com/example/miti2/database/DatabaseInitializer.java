package com.example.miti2.database;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.miti2.database.Cookie.CookieDatabase;
import com.example.miti2.database.Session.Session;
import com.example.miti2.database.Session.SessionDatabase;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final SessionDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final SessionDatabase db) {
        populateWithTestData(db);
    }

    private static Session addUser(final SessionDatabase db, Session user) {
        db.userDao().insertAll(user);
        return user;
    }

    private static void populateWithTestData(SessionDatabase db) {
        Session user = new Session();
        user.setFirstName("Ajay");
        user.setLastName("Saini");
//        user.setAge(25);
        addUser(db, user);

        List<Session> userList = db.userDao().getAll();
        Log.e("Apoorva Control", "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final SessionDatabase mDb;

        PopulateDbAsync(SessionDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            populateWithTestData(mDb);
            return null;
        }

    }
}
