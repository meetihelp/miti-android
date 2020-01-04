package com.miti.meeti.database.Chat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.miti.meeti.database.DatabaseInit;
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
    public void delete(String ...chatid){
        new DeleteChatAsyncTask(chatDbDao).execute(chatid[0]);
    }
    public void Synced(String ...chatid){
        new UpdateSyncAsyncTask(chatDbDao).execute(chatid[0]);
    }
    public void getmax(String ...chatid){
        new GetMaxAsyncTask(chatDbDao).execute(chatid[0]);
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
    private static class UpdateSyncAsyncTask extends AsyncTask<String, Void,Void> {
        private ChatDbDao chatDbDao;
        private UpdateSyncAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected Void doInBackground(String ...chatid) {
            chatDbDao.Synced(chatid[0]);
            return null;
        }
    }
    private static class GetMaxAsyncTask extends AsyncTask<String, Void,Integer> {
        private ChatDbDao chatDbDao;
        private GetMaxAsyncTask(ChatDbDao chatDbDao){
            this.chatDbDao=chatDbDao;
        }
        @Override
        protected Integer doInBackground(String ...chatid) {
            ChatDb temp=chatDbDao.getmax(chatid[0]);
            if(temp==null){
                return new Integer(0);
            }
            return temp.Indexz;
        }

        @Override
        protected void onPostExecute(Integer i) {
            social_chat_content.dbcallback(i.intValue());
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
    }
}
