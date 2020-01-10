package com.miti.meeti.database.Feed;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.miti.meeti.database.DatabaseInit;

import java.util.List;

public class FeedDbRepository {
    private FeedDbDao feedDbDao;
    private LiveData<List<FeedDb>> allfeedDbs;
    private static final String TAG = FeedDbRepository.class.getName();
    public FeedDbRepository(Application application){
        DatabaseInit database=DatabaseInit.getInstance(application);
        this.feedDbDao=database.feedDbDao();
        this.allfeedDbs=feedDbDao.getall();
    }
    public LiveData<List<FeedDb>> getall(){
        return allfeedDbs;
    }
    public void insert(FeedDb ...feedDb){
        new InsertfeedDbAsyncTask(feedDbDao).execute(feedDb);
    }
    public void react(String ...temp){
        new ReactfeedDbAsyncTask(feedDbDao).execute(temp);
    }
    public Integer getmax(){
        try{
            return new GetMaxfeedDbAsyncTask(feedDbDao).execute().get();
        }catch (Exception e){
            return null;
        }

    }
    private static class InsertfeedDbAsyncTask extends AsyncTask<FeedDb, Void,Void> {
        private FeedDbDao feedDbDao;
        private InsertfeedDbAsyncTask(FeedDbDao feedDbDao){
            this.feedDbDao=feedDbDao;
        }
        @Override
        protected Void doInBackground(FeedDb... feedDb) {
            feedDbDao.insert(feedDb);
            return null;
        }
    }
    private static class ReactfeedDbAsyncTask extends AsyncTask<String, Void,Void> {
        private FeedDbDao feedDbDao;
        private ReactfeedDbAsyncTask(FeedDbDao feedDbDao){
            this.feedDbDao=feedDbDao;
        }
        @Override
        protected Void doInBackground(String ...temp) {
            feedDbDao.reaction(temp[0],Integer.parseInt(temp[1]));
            return null;
        }
    }
    private static class GetMaxfeedDbAsyncTask extends AsyncTask<Void, Void,Integer> {
        private FeedDbDao feedDbDao;
        private GetMaxfeedDbAsyncTask(FeedDbDao feedDbDao){
            this.feedDbDao=feedDbDao;
        }
        @Override
        protected Integer doInBackground(Void ...temp) {
            int tempx=feedDbDao.getmax().Id;
            Integer tempu=new Integer(tempx);
            return tempu;
        }
    }

}
