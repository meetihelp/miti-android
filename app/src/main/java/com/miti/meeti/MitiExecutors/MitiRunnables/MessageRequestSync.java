package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.NetworkObjects.ImageUploadResponse;
import com.miti.meeti.database.Request.MessageRq;
import com.miti.meeti.database.Request.MessageRqViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.network.UploadImage;

import java.util.List;

public class MessageRequestSync implements Runnable {
    public class request{
        public String RequestId;
        public String Phone;
        public String MessageType;
        public String MessageContent;
        public request(){}
        public request(String requestId,String phone,String messageType,String messageContent){
            this.RequestId=requestId;
            this.Phone=phone;
            this.MessageContent=messageContent;
            this.MessageType=messageType;
        }
    }
    public class response{
        public Integer Code;
        public String Message;
        public String CreatedAt;
        public String RequestId;
    }
    @Override
    public void run() {
        Gson gson=new Gson();
        MessageRqViewModel messageRqViewModel= MainActivity.messageRqViewModel;
//        Mlog.e("MessageRequestSync","entered");
        List<MessageRq> temp=messageRqViewModel.getallnotsynced();
        if(temp==null||temp.size()==0){
            return;
        }
        Mlog.e("MessageRequestSync","started");
        for(MessageRq tempx:temp){
            new Mlog<MessageRq>().e1(tempx);
            if(tempx.MessageType.contains("text")){
                Mlog.e("MessageRequestSync","in text block");
                //String requestId,String phone,String messageType,String messageContent
                request reqp=new request(tempx.RequestId,tempx.Phone,tempx.MessageType,tempx.MessageContent);
                Mlog.e("MessageRequestSync","in line 54");
                String json=gson.toJson(reqp);
                Mlog.e(json);
                RequestHelper requestHelper=new SimplePOST().execute(AllUrl.url_chat().get(4),json,MainActivity.MeetiCookie);
                Mlog.e("MessageRequestSync","in line 55");
                if(requestHelper==null){
                    continue;
                }
                String res=requestHelper.getData();
                DiarySync.response resobj=gson.fromJson(res,DiarySync.response.class);
                if(resobj==null){
                    continue;
                }
                if(resobj.Code==200){
                    //String requestId,String contentid,String createdAt
                    messageRqViewModel.update(tempx.RequestId,resobj.CreatedAt);
                }else if(resobj.Code==1002 || resobj.Code==1002){
                    messageRqViewModel.delete(tempx);
                }
            }else if(tempx.MessageType.contains("image")){
                Mlog.e("MessageRequestSync","in image block");
                //param1 suburl, param2 fileaddress, param3 filename, param4 requestid, param5 public
                try{
                    String res=new UploadImage().execute(AllUrl.url_image().get(0),tempx.MessageContent,tempx.MessageContent,tempx.RequestId
                            ,"Private").get();
                    if(res==null){
                        Mlog.e("MessageRequestSync","image upload failed");
                        return;
                    }
                    ImageUploadResponse imgur=gson.fromJson(res,ImageUploadResponse.class);
                    Mlog.e("MessageRequestSync","after image upload");
                    if(imgur==null){
                        return;
                    }
                    if(imgur.Code==200){
                        Mlog.e("MessageRequestSync","image upload 200");
                        //String requestId,String phone,String messageType,String messageContent
                        String json=gson.toJson(new request(tempx.RequestId,tempx.Phone,tempx.MessageType,imgur.ImageId));
                        RequestHelper requestHelper=new SimplePOST().execute(AllUrl.url_chat().get(4),json,MainActivity.MeetiCookie);
                        if(requestHelper==null){
                            continue;
                        }
                        String resx=requestHelper.getData();
                        Mlog.e("messageRq syncing->",resx);
                        DiarySync.response resobj=gson.fromJson(resx,DiarySync.response.class);
                        if(resobj==null){
                            Mlog.e("messageRq syncing->","found null");
                            continue;
                        }
                        if(resobj.Code==200){
                            Mlog.e("messageRq syncing->","200 wala block ye");
                            Mlog.e(tempx.RequestId,imgur.ImageId,resobj.ContentId,resobj.CreatedAt);
                            //String requestId,String contentid,String createdAt
                            messageRqViewModel.updateimage(tempx.RequestId,imgur.ImageId);
                            messageRqViewModel.update(tempx.RequestId,resobj.CreatedAt);
                            Mlog.e("messageRq updated");
                        }else if(resobj.Code==1002 || resobj.Code==1002){
                            messageRqViewModel.delete(tempx);
                        }
                        Mlog.e("messageRq synced->",tempx.RequestId,imgur.ImageId);
                    }
                }catch (Exception e){

                }
            }
        }
    }
}
