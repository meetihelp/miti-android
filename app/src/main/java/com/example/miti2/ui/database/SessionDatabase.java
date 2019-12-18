package com.example.miti2.ui.database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Session.class,exportSchema = false,version = 1)
public abstract class SessionDatabase extends RoomDatabase {
    private static final String DB_NAME="session_db";
    private static SessionDatabase instance;

    public static synchronized SessionDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(),SessionDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }

    public static SessionDao sessionDao() {
        return new SessionDao() {
            @Override
            public String getCookie() {
                return null;
            }

            @Override
            public void InsertSession(Session session) {

            }

            @Override
            public void DeleteSession(Session session) {

            }
        };
    }

    public static class SessionDatabaseInsert extends AsyncTask<String,Void,Void>{

        private final SessionDao sessionDao;
        public SessionDatabaseInsert(SessionDatabase instance){
            sessionDao=instance.sessionDao();
        }

        @Override
        protected Void doInBackground(String... params) {
            Session session=new Session(params[0]);
            Log.e("do in Background",params[0]);
            if(sessionDao!=null) {
                sessionDao().InsertSession(session);
            }else{
                Log.e("Do in bg 2","Yahan laure lage");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public static class SessionDatabaseQuery extends AsyncTask<Void,Void,String>{

        private final SessionDao sessionDao;
        public SessionDatabaseQuery(SessionDatabase instance){
            sessionDao=instance.sessionDao();
        }

        @Override
        protected String doInBackground(Void... params) {
            String result=sessionDao().getCookie();
            if(result!=null){
                Log.e("Query",result);
            }
            else{
                Log.e("Query","Yahan nahi aaya");
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
}
