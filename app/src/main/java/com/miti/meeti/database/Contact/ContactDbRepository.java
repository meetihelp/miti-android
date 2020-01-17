package com.miti.meeti.database.Contact;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.miti.meeti.database.DatabaseInit;
import com.miti.meeti.mitiutil.Logging.Mlog;

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
    public List<ContactDb> getallnotsynced(){
        try{
            return new GetcontactDbAsyncTask(contactDbDao).execute().get();
        }catch (Exception e){
            return null;
        }

    }
    public void insert(ContactDb ...contactDb){
        new InsertcontactDbAsyncTask(contactDbDao).execute(contactDb);
    }
    public void delete(ContactDb ...contactDb){
        new DeletecontactDbAsynTask(contactDbDao).execute(contactDb);
    }
    public void update(Integer ...temp){
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
    private static class GetcontactDbAsyncTask extends AsyncTask<Void, Void,List<ContactDb>> {
        private ContactDbDao contactDbDao;
        private GetcontactDbAsyncTask(ContactDbDao contactDbDao){
            this.contactDbDao=contactDbDao;
        }
        @Override
        protected List<ContactDb> doInBackground(Void ...temp) {
            return contactDbDao.getallcontact();
        }
    }
    private static class DeletecontactDbAsynTask extends AsyncTask<ContactDb,Void,Void>{
        private ContactDbDao contactDbDao;
        public DeletecontactDbAsynTask(ContactDbDao contactDbDao) {
            this.contactDbDao=contactDbDao;
        }

        @Override
        protected Void doInBackground(ContactDb... contactDbs) {
            contactDbDao.delete(contactDbs);
            return null;
        }
    }
    private static class UpdatecontactDbAsyncTask extends AsyncTask<Integer, Void,Void> {
        private ContactDbDao contactDbDao;
        private UpdatecontactDbAsyncTask(ContactDbDao contactDbDao){
            this.contactDbDao=contactDbDao;
        }
        @Override
        protected Void doInBackground(Integer ...temp) {
            contactDbDao.update(temp[0],temp[1]);
            return null;
        }
    }
}
