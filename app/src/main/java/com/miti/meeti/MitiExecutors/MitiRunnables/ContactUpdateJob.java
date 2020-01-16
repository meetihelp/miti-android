package com.miti.meeti.MitiExecutors.MitiRunnables;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.ContactSync;
import com.miti.meeti.database.Contact.ContactDb;
import com.miti.meeti.database.Contact.ContactDbViewModel;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;

import java.util.ArrayList;
import java.util.List;

public class ContactUpdateJob implements Runnable {
    Gson gson=new Gson();
    CookieViewModel cookieViewModel=MainActivity.cookieViewModel;
    ContactDbViewModel contactDbViewModel=MainActivity.contactDbViewModel;
    @Override
    public void run() {
        String cookie=cookieViewModel.getCookie1();
        ContactDbViewModel contactDbViewModel= MainActivity.contactDbViewModel;
        List<ContactDb>all=contactDbViewModel.getallnotsynced();
        if(all==null||all.size()==0){
            Mlog.e("ContactUpdateJob","allsynced");
        }
        List<ContactSync.phone_object>all1=new ArrayList<>();
        for(ContactDb temp:all){
            ContactSync.phone_object tempx=new ContactSync().new phone_object(temp.Phone);
            all1.add(tempx);
        }
        ContactSync.request tempy=new ContactSync().new request(all1);
        String request=gson.toJson(tempy);
        POSTRequest postRequest=new POSTRequest();
        try{
            String response=postRequest.execute("getPhoneStatus",request,cookie).get().getData();
            if(response==null){
                Mlog.e("ContactUpdateJob","response null");
                return;
            }
            ContactSync.response tempz=gson.fromJson(response,ContactSync.response.class);
            if(tempz==null){
                Mlog.e("ContactUpdateJob","gson object null");
                return;
            }
            if(tempz.Code==200){
                List<Integer>tempo=tempz.PhoneStatus;
                for(int i=0;i<all1.size();i++){
                    contactDbViewModel.update(Integer.toString(tempo.get(i)),all1.get(i).Phone);
                }
            }
        }catch (Exception e){
            Mlog.e("ContactUpdateJob",e.toString());
        }
    }
}
