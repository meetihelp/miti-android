package com.miti.meeti.database.Request;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.miti.meeti.database.DatabaseInit;

import java.util.List;

public class MessageRqRepository {
    private MessageRqDao messageRqDao;
    private LiveData<List<MessageRq>> allmessageRqs;
    private static final String TAG = MessageRqRepository.class.getName();
    public MessageRqRepository(Application application){
        DatabaseInit database=DatabaseInit.getInstance(application);
        this.messageRqDao=database.messageRqDao();
        this.allmessageRqs=messageRqDao.getall();
    }
    public LiveData<List<MessageRq>> getall(){
        return allmessageRqs;
    }
    public List<MessageRq> getallnotsynced(){
        try{
            return new NotSyncedmessageRq(messageRqDao).execute().get();
        }catch (Exception e){
            return null;
        }

    }
    public void insert(MessageRq ...messageRq){
        new InsertmessageRqAsyncTask(messageRqDao).execute(messageRq);
    }
    public void delete(MessageRq ...messageRq){
        new DeletemessageRqAsyncTask(messageRqDao).execute(messageRq);
    }
    public void update(String ...temp){
        new UpdatemessageRqAsyncTask(messageRqDao).execute(temp);
    }
    public void updateimage(String ...temp){
        new UpdateImageAsyncTask(messageRqDao).execute(temp[0],temp[1]);
    }
    public MessageRq getmax(){
        try{
            return new GetmaxmessageRq(messageRqDao).execute().get();
        }catch (Exception e){
            return null;
        }
    }
    private static class DeletemessageRqAsyncTask extends AsyncTask<MessageRq, Void,Void> {
        private MessageRqDao messageRqDao;
        private DeletemessageRqAsyncTask(MessageRqDao messageRqDao){
            this.messageRqDao=messageRqDao;
        }
        @Override
        protected Void doInBackground(MessageRq... messageRq) {
            messageRqDao.delete(messageRq);
            return null;
        }
    }
    private static class InsertmessageRqAsyncTask extends AsyncTask<MessageRq, Void,Void> {
        private MessageRqDao messageRqDao;
        private InsertmessageRqAsyncTask(MessageRqDao messageRqDao){
            this.messageRqDao=messageRqDao;
        }
        @Override
        protected Void doInBackground(MessageRq... messageRq) {
            messageRqDao.insert(messageRq);
            return null;
        }
    }
    private static class GetmaxmessageRq extends AsyncTask<Void,Void,MessageRq> {
        private MessageRqDao messageRqDao;

        public GetmaxmessageRq(MessageRqDao messageRqDao){
            this.messageRqDao=messageRqDao;
        }

        @Override
        protected MessageRq doInBackground(Void... voids) {
            return messageRqDao.getmax();
        }
    }
    private static class NotSyncedmessageRq extends AsyncTask<Void,Void,List<MessageRq>>{
        private MessageRqDao messageRqDao;
        public NotSyncedmessageRq(MessageRqDao messageRqDao) {
            this.messageRqDao=messageRqDao;
        }

        @Override
        protected List<MessageRq> doInBackground(Void... voids) {
            List<MessageRq>temp=messageRqDao.getallnotsynced();
            return temp;
        }
    }
    private static class UpdateImageAsyncTask extends AsyncTask<String, Void,Void> {
        private MessageRqDao messageRqDao;
        private UpdateImageAsyncTask(MessageRqDao messageRqDao){
            this.messageRqDao=messageRqDao;
        }
        @Override
        protected Void doInBackground(String ...temp) {
            messageRqDao.updateimage(temp[0],temp[1]);
            return null;
        }
    }
    private static class UpdatemessageRqAsyncTask extends AsyncTask<String, Void,Void> {
        private MessageRqDao messageRqDao;
        private UpdatemessageRqAsyncTask(MessageRqDao messageRqDao){
            this.messageRqDao=messageRqDao;
        }
        @Override
        protected Void doInBackground(String ...temp) {
            messageRqDao.update(temp[0],temp[1]);
            return null;
        }
    }
}
