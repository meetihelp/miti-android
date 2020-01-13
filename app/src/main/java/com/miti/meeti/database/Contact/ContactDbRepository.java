package com.miti.meeti.database.Contact;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.miti.meeti.database.DatabaseInit;

import java.util.List;

public class ContactDbRepository {
    private ContactDbDao contactDbDao;
    private LiveData<List<ContactDb>> allcontactDbs;
    private static final String TAG = ContactDbRepository.class.getName();
    public ContactDbRepository(Application application){
        DatabaseInit database=DatabaseInit.getInstance(application);
        this.contactDbDao=database.contactDbDao();
        this.allcontactDbs=contactDbDao.getall();
    }
    public LiveData<List<ContactDb>> getall(){
        return allcontactDbs;
    }
    public void insert(ContactDb ...contactDb){
        new InsertcontactDbAsyncTask(contactDbDao).execute(contactDb);
    }
    public void update(String ...temp){
        new UpdatecontactDbAsyncTask(contactDbDao).execute(temp);
    }
    private static class InsertcontactDbAsyncTask extends AsyncTask<ContactDb, Void,Void> {
        private ContactDbDao contactDbDao;
        private InsertcontactDbAsyncTask(ContactDbDao contactDbDao){
            this.contactDbDao=contactDbDao;
        }
        @Override
        protected Void doInBackground(ContactDb... contactDb) {
            contactDbDao.insert(contactDb);
            return null;
        }
    }
    private static class UpdatecontactDbAsyncTask extends AsyncTask<String, Void,Void> {
        private ContactDbDao contactDbDao;
        private UpdatecontactDbAsyncTask(ContactDbDao contactDbDao){
            this.contactDbDao=contactDbDao;
        }
        @Override
        protected Void doInBackground(String ...temp) {
            contactDbDao.update(Integer.parseInt(temp[0]),temp[1]);
            return null;
        }
    }
}
