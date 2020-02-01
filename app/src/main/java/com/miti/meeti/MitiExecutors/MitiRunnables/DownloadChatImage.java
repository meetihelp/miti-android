package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.NetworkObjects.GetImageUrl;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Chat.ChatDbViewModel;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.DownloadAndSaveImage;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.try123;

import java.io.File;
import java.util.List;

public class DownloadChatImage implements Runnable{
    Gson gson=new Gson();
    public static String pathhelper(String subdir,String prefix){
        String n= try123.randomAlphaNumeric(32);
        String fname = prefix + n + ".jpg";
        String path= MainActivity.RootFolder+ File.separator+subdir;
        try123.createifnot(path);
        return path+File.separator+fname;
    }

    @Override
    public void run() {
//        Mlog.e("inDownloadChatImage","started");
        ChatDbViewModel chatDbViewModel=MainActivity.chatDbViewModel;
        CookieViewModel cookieViewModel=MainActivity.cookieViewModel;
        String cookie=cookieViewModel.getCookie1();
        List<ChatDb>notsync=chatDbViewModel.getnotsyncedimage();
//        Mlog.e("inDownloadChatImage","line 34");
        if(notsync==null || notsync.size()==0){return;}
        Mlog.e("inDownloadChatImage","line 36 not null");
        for(ChatDb temp:notsync){
            String path=pathhelper("Received","Chats");
            POSTRequest postRequest=new POSTRequest();
            try{
                Mlog.e("inDownloadChatImage41",temp.MessageContent);
                String json=gson.toJson(new GetImageUrl().new request_body(temp.MessageContent));
                String response=postRequest.execute(AllUrl.url_image().get(1),json,cookie).get().getData();
                GetImageUrl.response_body rb=gson.fromJson(response,GetImageUrl.response_body.class);
                if(rb==null){
                    continue;
                }
                Mlog.e("inDownloadChatImage48",response);
                if(rb.Code==200){
                    if(rb.ImageURL.length()==0 || rb.ImageURL==null){
                        Mlog.e("inDownloadChatImage","image url empty");
                        continue;
                    }
                    Boolean ast=new DownloadAndSaveImage(path).execute(rb.ImageURL).get();
                    if(ast){
                        chatDbViewModel.updatesyncimage(temp.MessageId,path);
                    }
                }
            }catch (Exception e){
                Mlog.e("inDownloadChatImage",e.toString());
            }
        }
    }
}
