package com.miti.meeti.database.Keyvalue;

import android.app.Application;
import android.os.AsyncTask;

import com.miti.meeti.database.DatabaseInit;
import com.miti.meeti.mitiutil.Logging.Mlog;

public class KeyvalueRepository {
    private KeyvalueDao kd;
    public KeyvalueRepository(Application application){
        DatabaseInit kvDatabase= DatabaseInit.getInstance(application);
        this.kd=kvDatabase.keyvalueDao();
    }
    public void insert(keyvalue ...kp){
        new InsertKPAsyncTask(kd).execute(kp);
    }
    public keyvalue get(String key){
        try{
            return new GetKPAsyncTask(kd).execute(key).get();
        }catch (Exception e){
            Mlog.e("Key not found");
        }
        return null;
    }
    private static class InsertKPAsyncTask extends AsyncTask<keyvalue, Void,Void> {
        KeyvalueDao kd;
        public InsertKPAsyncTask(KeyvalueDao kd){
            this.kd=kd;
        }

        @Override
        protected Void doInBackground(keyvalue... keyvalues) {
            kd.insert(keyvalues);
            return null;
        }
    }
    private static class GetKPAsyncTask extends AsyncTask<String, Void,keyvalue> {
        KeyvalueDao kd;
        public GetKPAsyncTask(KeyvalueDao kd){
            this.kd=kd;
        }

        @Override
        protected keyvalue doInBackground(String... keyvalues) {
            return kd.get(keyvalues[0]);
        }
    }
}
