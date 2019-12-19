package com.miti.meeti.database.Cookie;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

public class CookieDatabaseHelper {
    private static final String TAG = CookieDatabaseHelper.class.getName();

    public static void populateAsync(@NonNull final CookieDatabase db,String MeetiCookie) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute(MeetiCookie);
    }

    public static void populateSync(@NonNull final CookieDatabase db,String MeetiCokie) {
        populateWithTestData(db,MeetiCokie);
    }

    private static Cookie addCookie(final CookieDatabase db, Cookie cookie) {
        db.cookieDao().insertCookie(cookie);
        return cookie;
    }

    private static void populateWithTestData(CookieDatabase db,String MeetiCookie) {
        Cookie cookie = new Cookie();
        cookie.setMeetiCookie(MeetiCookie);
//        user.setAge(25);
        addCookie(db, cookie);

//        List<Session> userList = db.userDao().getAll();
//        Log.e("Apoorva Control", "Rows Count: " + userList.size());
    }

    private static class PopulateDbAsync extends AsyncTask<String, Void, Void> {

        private final CookieDatabase mDb;

        PopulateDbAsync(CookieDatabase db) {
            mDb = db;
        }

        @Override
        protected Void doInBackground(final String... params) {
            populateWithTestData(mDb,params[0]);
            return null;
        }

    }
}
