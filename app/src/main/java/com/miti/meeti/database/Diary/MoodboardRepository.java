package com.miti.meeti.database.Diary;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.miti.meeti.database.DatabaseInit;

import java.util.List;

public class MoodboardRepository {
    private MoodboardDao moodboardDao;
    private LiveData<List<Moodboard>> allmoodboards;
    private static final String TAG = MoodboardRepository.class.getName();
    public MoodboardRepository(Application application){
        DatabaseInit database=DatabaseInit.getInstance(application);
        this.moodboardDao=database.moodboardDao();
        this.allmoodboards=moodboardDao.getall();
    }
    public LiveData<List<Moodboard>> getall(){
        return allmoodboards;
    }
    public List<Moodboard> getallnotsynced(){
        try{
            return new NotSyncedmoodboard(moodboardDao).execute().get();
        }catch (Exception e){
            return null;
        }

    }
    public void insert(Moodboard ...moodboard){
        new InsertmoodboardAsyncTask(moodboardDao).execute(moodboard);
    }
    public void delete(Moodboard ...moodboard){
        new DeletemoodboardAsyncTask(moodboardDao).execute(moodboard);
    }
    public void update(String ...temp){
        new UpdatemoodboardAsyncTask(moodboardDao).execute(temp);
    }
    public void updateimage(String ...temp){
        new UpdateImageAsyncTask(moodboardDao).execute(temp[0],temp[1]);
    }
    public void updateContent(String ...temp){
        new UpdateContentmoodboardAsyncTask(moodboardDao).execute(temp);
    }
    public Moodboard getmax(){
        try{
            return new GetMaxmoodboardAsyncTask(moodboardDao).execute().get();
        }catch (Exception e){
            return null;
        }

    }
    private static class DeletemoodboardAsyncTask extends AsyncTask<Moodboard, Void,Void> {
        private MoodboardDao moodboardDao;
        private DeletemoodboardAsyncTask(MoodboardDao moodboardDao){
            this.moodboardDao=moodboardDao;
        }
        @Override
        protected Void doInBackground(Moodboard... moodboard) {
            moodboardDao.delete(moodboard);
            return null;
        }
    }
    private static class InsertmoodboardAsyncTask extends AsyncTask<Moodboard, Void,Void> {
        private MoodboardDao moodboardDao;
        private InsertmoodboardAsyncTask(MoodboardDao moodboardDao){
            this.moodboardDao=moodboardDao;
        }
        @Override
        protected Void doInBackground(Moodboard... moodboard) {
            moodboardDao.insert(moodboard);
            return null;
        }
    }
    private static class NotSyncedmoodboard extends AsyncTask<Void,Void,List<Moodboard>>{
        private MoodboardDao moodboardDao;
        public NotSyncedmoodboard(MoodboardDao moodboardDao) {
            this.moodboardDao=moodboardDao;
        }

        @Override
        protected List<Moodboard> doInBackground(Void... voids) {
            List<Moodboard>temp=moodboardDao.getallnotsynced();
            return temp;
        }
    }
    private static class UpdateImageAsyncTask extends AsyncTask<String, Void,Void> {
        private MoodboardDao moodboardDao;
        private UpdateImageAsyncTask(MoodboardDao moodboardDao){
            this.moodboardDao=moodboardDao;
        }
        @Override
        protected Void doInBackground(String ...temp) {
            moodboardDao.updateimage(temp[0],temp[1]);
            return null;
        }
    }
    private static class UpdatemoodboardAsyncTask extends AsyncTask<String, Void,Void> {
        private MoodboardDao moodboardDao;
        private UpdatemoodboardAsyncTask(MoodboardDao moodboardDao){
            this.moodboardDao=moodboardDao;
        }
        @Override
        protected Void doInBackground(String ...temp) {
            moodboardDao.update(temp[0],temp[1],temp[2]);
            return null;
        }
    }
    private static class UpdateContentmoodboardAsyncTask extends AsyncTask<String, Void,Void> {
        private MoodboardDao moodboardDao;
        private UpdateContentmoodboardAsyncTask(MoodboardDao moodboardDao){
            this.moodboardDao=moodboardDao;
        }
        @Override
        protected Void doInBackground(String ...temp) {
            moodboardDao.updateContent(temp[0],temp[1]);
            return null;
        }
    }
    private static class GetMaxmoodboardAsyncTask extends AsyncTask<Void, Void,Moodboard> {
        private MoodboardDao moodboardDao;
        private GetMaxmoodboardAsyncTask(MoodboardDao moodboardDao){
            this.moodboardDao=moodboardDao;
        }
        @Override
        protected Moodboard doInBackground(Void ...temp) {
            return moodboardDao.getmax();
        }
    }
}
