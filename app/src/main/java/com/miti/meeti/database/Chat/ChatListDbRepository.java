package com.miti.meeti.database.Chat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.miti.meeti.NetworkObjects.ChatList;
import com.miti.meeti.database.DatabaseInit;

public class ChatListDbRepository {
    private ChatListDbDao chatListDbDao;
    private LiveData<ChatListDb[]> chatlist;
    public ChatListDbRepository(Application application){
        DatabaseInit Database=DatabaseInit.getInstance(application);
        this.chatListDbDao=Database.chatListDbDao();
        this.chatlist=chatListDbDao.getall();
    }
    public LiveData<ChatListDb[]> getall(){
        return this.chatlist;
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
}
