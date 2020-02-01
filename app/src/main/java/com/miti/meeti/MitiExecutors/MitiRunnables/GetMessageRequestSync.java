package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.AllUrl;
import com.miti.meeti.database.Request.MessageRq;
import com.miti.meeti.database.Request.MessageRqViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.ui.social.chat.Message;

import java.util.ArrayList;
import java.util.List;

public class GetMessageRequestSync implements Runnable {
    Gson gson=new Gson();
    public class request{
        public String CreatedAt;
        public request(){}
        public request(String createdAt){
            this.CreatedAt=createdAt;
        }
    }
    public class mitiObject{
        public String Name;
        public String Phone;
        public String CreatedAt;
    }
    public class response{
        public Integer Code;
        public String Message;
        List<mitiObject> RequestList;
    }
    @Override
    public void run() {
        MessageRqViewModel messageRqViewModel= MainActivity.messageRqViewModel;
        String max;
        MessageRq maxob=messageRqViewModel.getmax();
        if(maxob==null){
            max=new String("");
        }else{
            max=maxob.CreatedAt;
        }
        Mlog.e("GetMessageRequestSync",max);
        String req=gson.toJson(new request(max));
        RequestHelper resp=new SimplePOST().execute(AllUrl.url_chat().get(3),req,MainActivity.MeetiCookie);
        if(resp==null){
            Mlog.e("GetMessageRequestSync","null1");
            return;
        }
        response respo=gson.fromJson(resp.getData(),response.class);
        if(respo==null){
            Mlog.e("GetMessageRequestSync","null2");
            return;
        }
        if(respo.Code==200){
            List<mitiObject>lkj=respo.RequestList;
            List<MessageRq>lpo=new ArrayList<>();
            for(mitiObject lkjh:lkj){
                MessageRq messageRq=new MessageRq();
                messageRq.Tag="received";
                messageRq.Phone=lkjh.Phone;
                messageRq.Name=lkjh.Name;
                messageRq.CreatedAt=lkjh.CreatedAt;
                messageRq.Sync=1;
                messageRq.RequestId= try123.randomAlphaNumeric(16);
                messageRq.UserCreatedAt=try123.mitidt();
                lpo.add(messageRq);
            }
            messageRqViewModel.insert(lpo.toArray(new MessageRq[lpo.size()]));
        }
    }
}
