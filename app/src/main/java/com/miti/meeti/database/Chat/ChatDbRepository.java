package com.miti.meeti.database.Chat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.miti.meeti.database.DatabaseInit;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.ui.social.chat.social_chat_content;

import java.util.List;

public class ChatDbRepository {
    private ChatDbDao chatDbDao;
    private LiveData<List<ChatDb>> chats;
    public ChatDbRepository(Application application){
        DatabaseInit Database=DatabaseInit.getInstance(application);
        this.chatDbDao=Database.chatDbDao();
    }
    public LiveData<List<ChatDb>>getchatbyid(String chatid){
        this.chats=chatDbDao.getchatbyid(chatid);
        return chats;
    }
    public void insert(ChatDb ...chats){
        new InsertChatAsyncTask(chatDbDao).execute(chats);
    }
    public void insertnew(ChatDb ...chats){
        new InsertChatNewAsyncTask(chatDbDao).execute(chats);
    }
    public void delete(String ...chatid){
        new DeleteChatAsyncTask(chatDbDao).execute(chatid[0]);
    }
    public void Synced(String ...strings){
        new UpdateSyncAsyncTask(chatDbDao).execute(strings);
    }
    public void SyncedImage(String ...strings){
        new UpdateImageSyncAsyncTask(chatDbDao).execute(strings);
    }
    public void getnotlive(String chatid){
        new GetNotLiveAsyncTask(chatDbDao).execute(chatid);
    }
    //UpdatesyncimageAsyncTask
    public void updatesyncimage(String messageid,String content){
        new UpdatesyncimageAsyncTask(chatDbDao).execute(messageid,content);
    }
    public List<ChatDb> getnotsync(){
        try{
            return new GetNotSyncAsyncTask(chatDbDao).execute().get();
        }catch (Exception e){
            return null;
        }

    }
    public List<ChatDb> getnotsyncedimage(){
        try{
            return new Getnotsyncedimage(chatDbDao).execute().get();
        }catch (Exception e){
            return null;
        }
    }
    public String getmax(String ...chatid){
        try{
            Mlog.e("ingetmax");
            return new GetMaxAsyncTask(chatDbDao).execute(chatid[0]).get();
        }catch (Exception e){
            return null;
        }

    }
    public void ifrow(String ...chatid){
        new IfrowAsyncTask(chatDbDao).execute(chatid[0]);
    }
    private static class DeleteChatAsyncTask extends AsyncTask<String, Void,Void> {
        private ChatDbDao chatDbDao;
        private DeleteChatAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected Void doInBackground(String ...chatid) {
            chatDbDao.deleteChat(chatid[0]);
            return null;
        }
    }
    private static class UpdatesyncimageAsyncTask extends AsyncTask<String, Void,Void> {
        private ChatDbDao chatDbDao;
        private UpdatesyncimageAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected Void doInBackground(String ...chatid) {
            chatDbDao.updatesyncimage(chatid[0],chatid[1]);
            return null;
        }
    }
    private static class UpdateImageSyncAsyncTask extends AsyncTask<String, Void,Void> {
        private ChatDbDao chatDbDao;
        private UpdateImageSyncAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected Void doInBackground(String ...chatid) {
            chatDbDao.SyncedImage(chatid[0],chatid[1],chatid[2],chatid[3],chatid[4]);
            return null;
        }
    }
    private static class UpdateSyncAsyncTask extends AsyncTask<String, Void,Void> {
        private ChatDbDao chatDbDao;
        private UpdateSyncAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected Void doInBackground(String ...chatid) {
            chatDbDao.Synced(chatid[0],chatid[1],chatid[2]);
            return null;
        }
    }
    private static class GetMaxAsyncTask extends AsyncTask<String, Void,String> {
        private ChatDbDao chatDbDao;
        private GetMaxAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected String doInBackground(String ...chatid) {
            Mlog.e("chatid->",chatid[0],"ingetmaxasynctask");
            ChatDb temp=chatDbDao.getmax(chatid[0]);
            if(temp==null){
                Mlog.e("max->","");
                return new String("");
            }
            Mlog.e("max->",temp.CreatedAt);
            return temp.CreatedAt;
        }
    }
    private static class IfrowAsyncTask extends AsyncTask<String, Void,String> {
        private ChatDbDao chatDbDao;
        private IfrowAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected String doInBackground(String ...chatid) {
            ChatDb temp=chatDbDao.ifrow(chatid[0]);
            if(temp==null){
                return new String("");
            }
            return temp.CreatedAt;
        }
    }
    private static class GetNotSyncAsyncTask extends AsyncTask<Void, Void,List<ChatDb>> {
        private ChatDbDao chatDbDao;
        private GetNotSyncAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected List<ChatDb> doInBackground(Void ...chatid) {
            List<ChatDb> temp=chatDbDao.getchatnotsynced();
            if(temp==null){
                return null;
            }
            return temp;
        }
    }
    private static class Getnotsyncedimage extends AsyncTask<String, Void,List<ChatDb>> {
        private ChatDbDao chatDbDao;
        private Getnotsyncedimage(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected List<ChatDb> doInBackground(String ...chatid) {
            List<ChatDb> temp=chatDbDao.getnotsyncedimage();
            if(temp==null){
                return null;
            }
            return temp;
        }
    }
    private static class GetNotLiveAsyncTask extends AsyncTask<String, Void,List<ChatDb>> {
        private ChatDbDao chatDbDao;
        private GetNotLiveAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected List<ChatDb> doInBackground(String ...chatid) {
            List<ChatDb> temp=chatDbDao.getchatnotlive(chatid[0]);
            if(temp==null){
                return null;
            }
            return temp;
        }
    }
    private static class InsertChatAsyncTask extends AsyncTask<ChatDb, Void,Void> {
        private ChatDbDao chatDbDao;
        private InsertChatAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected Void doInBackground(ChatDb... chat) {
            chatDbDao.insertChat(chat);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

        }
    }
    private static class InsertChatNewAsyncTask extends AsyncTask<ChatDb, Void,Void> {
        private ChatDbDao chatDbDao;
        private InsertChatNewAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected Void doInBackground(ChatDb... chat) {
            chatDbDao.insertChat(chat);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            social_chat_content.dbcallbacksendmessage();
        }
    }
}
