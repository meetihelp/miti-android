package com.miti.meeti.database.Cookie;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.miti.meeti.database.DatabaseInit;

import java.util.concurrent.ExecutionException;

public class CookieRepository {
    private CookieDao cookieDao;
    private LiveData<String[]> miticookie;
    private static final String TAG = CookieRepository.class.getName();
    public CookieRepository(Application application){
        DatabaseInit cookieDatabase=DatabaseInit.getInstance(application);
        this.cookieDao=cookieDatabase.cookieDao();
        this.miticookie=cookieDao.getCookie();
    }
    public void insert(Cookie cookie){
        new InsertCookieAsyncTask(cookieDao).execute(cookie);
    }
    public String getCookie1(){
        try {
            return new GetCookieAsyncTask(cookieDao).execute().get();
        } catch (ExecutionException e) {
            Log.e("Control",e.toString());
        } catch (InterruptedException e) {
//            e.printStackTrace();
            Log.e("Control",e.toString());
        }
        return "";
    }
    private static class GetCookieAsyncTask extends AsyncTask<Void, Void,String>{
        private CookieDao cookieDao;
        private GetCookieAsyncTask(CookieDao cookieDao){
            this.cookieDao=cookieDao;
        }
        @Override
        protected String doInBackground(Void... voids) {
            return cookieDao.getCookie1();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
    public LiveData<String[]> getCookie(){
        return miticookie;
    }
    private static class InsertCookieAsyncTask extends AsyncTask<Cookie, Void,Void>{
        private CookieDao cookieDao;
        private InsertCookieAsyncTask(CookieDao cookieDao){
            this.cookieDao=cookieDao;
        }
        @Override
        protected Void doInBackground(Cookie... cookie) {
            cookieDao.insertCookie(cookie[0]);
            return null;
        }
    }
}
