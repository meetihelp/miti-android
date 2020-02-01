package com.miti.meeti.database.Chat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.database.DatabaseInit;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.ui.social.chat.DefaultDialog;
import com.miti.meeti.ui.social.chat.social_chat_list;

import java.util.ArrayList;
import java.util.List;

public class ChatListDbRepository {
    private ChatListDbDao chatListDbDao;
    private LiveData<List<ChatListDb>> chatlist;
    public ChatListDbRepository(Application application){
        DatabaseInit Database=DatabaseInit.getInstance(application);
        this.chatListDbDao=Database.chatListDbDao();
        this.chatlist=chatListDbDao.getall();
    }
    public LiveData<List<ChatListDb>> getall(){
        return this.chatlist;
    }
    public ChatListDb getmax(){
        try{
            return new GetMaxAsyncTask(chatListDbDao).execute().get();
        }catch (Exception e){
            return null;
        }
    }
    public List<ChatListDb> getallold(){
        try{
            return new GetAllNotLiveAsyncTask(chatListDbDao).execute("").get();
        }catch (Exception e){
            return null;
        }
    }
    public void updatelast(String chatid, String lastupdate,String lastmessage){
        new UpdateLastAsyncTask(chatListDbDao).execute(chatid,lastupdate,lastmessage);
    }
    public void insert(ChatListDb ...chatlist){
        new InsertChatAsyncTask(chatListDbDao).execute(chatlist);
    }
    public void delete(String ...chatid){
        new DeleteChatAsyncTask(chatListDbDao).execute(chatid[0]);
    }
    public void Synced(String ...chatid){
        new UpdateSyncAsyncTask(chatListDbDao).execute(chatid[0]);
    }
    private static class DeleteChatAsyncTask extends AsyncTask<String, Void,Void> {
        private ChatListDbDao chatListDbDao;
        private DeleteChatAsyncTask(ChatListDbDao chatListDbDao){
            this.chatListDbDao=chatListDbDao;
        }
        @Override
        protected Void doInBackground(String ...chatid) {
            chatListDbDao.deleteChat(chatid[0]);
            return null;
        }
    }
    private static class UpdateSyncAsyncTask extends AsyncTask<String, Void,Void> {
        private ChatListDbDao chatListDbDao;
        private UpdateSyncAsyncTask(ChatListDbDao chatListDbDao){
            this.chatListDbDao=chatListDbDao;
        }
        @Override
        protected Void doInBackground(String ...chatid) {
            chatListDbDao.Synced(chatid[0]);
            return null;
        }
    }
    private static class InsertChatAsyncTask extends AsyncTask<ChatListDb, Void,Void> {
        private ChatListDbDao chatListDbDao;
        private InsertChatAsyncTask(ChatListDbDao ChatListDbDao){
            this.chatListDbDao=ChatListDbDao;
        }
        @Override
        protected Void doInBackground(ChatListDb... chatlist) {
            chatListDbDao.insert(chatlist);
            return null;
        }
    }
    private static class UpdateLastAsyncTask extends AsyncTask<String, Void,Void> {
        private ChatListDbDao chatListDbDao;
        private UpdateLastAsyncTask(ChatListDbDao ChatListDbDao){
            this.chatListDbDao=ChatListDbDao;
        }
        @Override
        protected Void doInBackground(String... config) {
            chatListDbDao.UpdateLast(config[0],config[1],config[2]);
            return null;
        }
    }
    private static class GetMaxAsyncTask extends AsyncTask<Void, Void,ChatListDb> {
        private ChatListDbDao chatListDbDao;
        private GetMaxAsyncTask(ChatListDbDao ChatListDbDao){
            this.chatListDbDao=ChatListDbDao;
        }
        @Override
        protected ChatListDb doInBackground(Void... config) {
            return chatListDbDao.getmax();
        }
    }
    private static class GetAllNotLiveAsyncTask extends AsyncTask<String, Void,List<ChatListDb>> {
        private ChatListDbDao chatListDbDao;
        private GetAllNotLiveAsyncTask(ChatListDbDao ChatListDbDao){
            this.chatListDbDao=ChatListDbDao;
        }

        @Override
        protected List<ChatListDb> doInBackground(String ...temp) {
            return chatListDbDao.getallnotlive();
        }
    }
}
