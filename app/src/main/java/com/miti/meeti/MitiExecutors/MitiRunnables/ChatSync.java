package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.ChatUploadResponse;
import com.miti.meeti.NetworkObjects.GetChatContent;
import com.miti.meeti.NetworkObjects.SendChatContent;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Chat.ChatDbViewModel;
import com.miti.meeti.database.Chat.ChatListDb;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.SendChatImage;
import com.miti.meeti.ui.social.chat.ChatContentRequest;

import java.util.ArrayList;
import java.util.List;

public class ChatSync implements Runnable{
    public static void helper_insert(List<ChatDb>messages){
        List<ChatDb>tempxy=new ArrayList<>();
        for(ChatDb tempx:messages){
            tempx.Sync=1;
            if(tempx.MessageContent.contains("image")){
                tempx.Sync=-3;
            }
            tempxy.add(tempx);
            Mlog.e("GetChatPost",tempx.CreatedAt);
        }
        MainActivity.chatDbViewModel.insert(tempxy.toArray(new ChatDb[tempxy.size()]));
    }
    @Override
    public void run(){
        run1();
        run2();
    }
    public void run1() {
        Mlog.e("inChatSync","started");
        String cookie=MainActivity.cookieViewModel.getCookie1();
        if(cookie==null){
            return;
        }
        ChatDbViewModel chatDbViewModel= MainActivity.chatDbViewModel;
        Gson gson=new Gson();
        List<ChatDb>temp=chatDbViewModel.getnotsync();
        if(temp==null || temp.size()==0){
            Mlog.e("inChatSync","nochat to sync");
            return;
        }
        Mlog.e("inChatSync","sizeofarray",Integer.toString(temp.size()));
        for(ChatDb tempx:temp){
            String maxdate=chatDbViewModel.getmax(tempx.ChatId);
            if(maxdate==null){
                maxdate="";
            }
            Mlog.e("inChatSync","getmax",maxdate);
            Mlog.e("inChatSync","",tempx.MessageContent,tempx.MessageType);
            if(tempx.MessageType.contains("text")){
                //String messageType, String messageContent,String chatId,String requestId
                SendChatContent.request_body message=new SendChatContent().new request_body(tempx.MessageType,
                        tempx.MessageContent,tempx.ChatId,tempx.RequestId,maxdate);
                String jsonInString = gson.toJson(message);
                POSTRequest postRequest=new POSTRequest();
                try{
                    Mlog.e("inChatSync","in line 58");
                    String response=postRequest.execute("chat",jsonInString,cookie).get().getData();
                    Mlog.e("inChatSync","in line 59",response);
                    SendChatContent.response_object response_object=gson.fromJson(response,SendChatContent.response_object.class);
                    Mlog.e("inChatSync","in line 62");
                    if(response_object==null){
                        Mlog.e("inChatSync","response object null");
                        continue;
                    }
                    if(response_object.Code==200){
                        Mlog.e("inChatSync","response 200");
                        if(response_object.Chat.size()!=0){
                            //String requestid,String messageid,String createdAt
                            helper_insert(response_object.Chat);
                        }
                        chatDbViewModel.synced(tempx.RequestId,response_object.MessageId,response_object.CreatedAt);
                        maxdate=response_object.CreatedAt;
                    }
                }catch (Exception e){
                    Mlog.e("inChatSync",e.toString());
                }

            }else if(tempx.MessageType.contains("image")){
                Mlog.e("Chat syncing for data","image",tempx.MessageContent);
                //param1 suburl, param2 fileaddress, param3 filename, param4 requestid, param5 public, param6 createdat
                SendChatImage imgu=new SendChatImage();
                try{
                    Mlog.e("imgur","in try block");
                    String imgurs=imgu.execute("sendChatImage",tempx.ImageUrl,tempx.ImageUrl
                    ,tempx.RequestId,"Private",maxdate,cookie,tempx.ChatId).get();
                    //String requestid,String messageid, String imageurl,String imageid
                    if(imgurs==null){
                        Mlog.e("Chat syncing for data","imgur null");
                        continue;
                    }
                    Mlog.e("imgurs",imgurs);
                    ChatUploadResponse imgur=gson.fromJson(imgurs, ChatUploadResponse.class);
                    Mlog.e("imgur",Integer.toString(imgur.Code));
                    if(imgur.Code==200){
                        Mlog.e("Chat syncing for data","imgur 200");
                        List<ChatDb>tempu=imgur.Chat;
                        if(tempu.size()!=0){
                            helper_insert(imgur.Chat);
                        }
                        //String requestid,String messageid, String imageid,String createdAt
                        chatDbViewModel.syncedimage(tempx.RequestId,imgur.Messageid,imgur.ImageId,imgur.CreatedAt);
                        maxdate=imgur.CreatedAt;
                    }
                    //String requestid,String CreatedAt,String messageid, String imageurl
                }catch (Exception e){
                    Mlog.e("inChatSyncimage",e.toString());
                }

            }
        }
    }
    public void run2(){
        //get messages
        List<ChatListDb>temp= MainActivity.chatListDbViewModel.getold();
        for(ChatListDb tempx:temp){
            Mlog.e("IN UpdateChatMEssages",tempx.ChatId,"");
            String datetime=MainActivity.chatDbViewModel.getmax(tempx.ChatId);
            ChatContentRequest.getmessage(new GetChatContent().new request_body(tempx.ChatId,10,datetime),MainActivity.cookieViewModel.getCookie1());
            try{
                Thread.sleep(2000);
            }catch (Exception e){
            }
        }
    }
}
