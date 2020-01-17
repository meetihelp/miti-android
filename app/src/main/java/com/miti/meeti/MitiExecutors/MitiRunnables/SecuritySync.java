package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Contact.ContactDb;
import com.miti.meeti.database.Contact.ContactDbViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.List;

public class SecuritySync implements Runnable {
    public class request{
        public String Phone;
        public String ChainId;
        public String Name;
        public String RequestId;
        public request(){}
        public request(String phone,String chainId, String name,String requestId){
            this.Phone=phone;
            this.ChainId=chainId;
            this.Name=name;
            this.RequestId=requestId;
        }
    }
    public class response{
        public Integer Code;
        public String Message;
        public String UpdatedAt;
        public String RequestId;
    }
    @Override
    public void run() {
        Gson gson=new Gson();
        ContactDbViewModel contactDbViewModel= MainActivity.contactDbViewModel;
        List<ContactDb>all=contactDbViewModel.getallnotsynced();
        Mlog.e("Security Sync Started");
        for(ContactDb temp:all){
            if(temp.Tag.contains("deleted")){
                Mlog.e("Security Sync Started","in deleted block");
                String json=gson.toJson(new SecuritySync().new request(temp.Phone,"Primary",temp.Name,temp.Requestid));
                RequestHelper requestHelper=new SimplePOST().execute("deletePrimaryTrustChain",json,MainActivity.MeetiCookie);
                if (requestHelper==null){
                    continue;
                }
                String data=requestHelper.getData();
                SecuritySync.response resp=gson.fromJson(data,SecuritySync.response.class);
                if(resp==null){
                    continue;
                }
                if(resp.Code==200){
                    //int status,int uid
                    contactDbViewModel.update(1,temp.uid);
                }
            }else{
                //tag is Board id;
                //String phone,String chainId, String name,String requestId
                Mlog.e("Security Sync Started","in send contact block");
                String json=gson.toJson(new SecuritySync().new request(temp.Phone,temp.Tag,temp.Name,temp.Requestid));
                RequestHelper requestHelper=new SimplePOST().execute("createPrimaryTrustChain",json,MainActivity.MeetiCookie);
                if (requestHelper==null){
                    continue;
                }
                String data=requestHelper.getData();
                SecuritySync.response resp=gson.fromJson(data,SecuritySync.response.class);
                if(resp==null){
                    continue;
                }
                if(resp.Code==200){
                    //int status,int uid
                    contactDbViewModel.update(1,temp.uid);
                }
            }
        }
        Mlog.e("Security Sync Ended");
    }
}
