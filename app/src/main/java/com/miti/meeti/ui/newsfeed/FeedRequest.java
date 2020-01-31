package com.miti.meeti.ui.newsfeed;

import android.util.Log;

import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.database.Feed.FeedDb;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.ArrayList;
import java.util.List;

public class FeedRequest {
    public static void getinitialnews(String cookie){
        List<Feed.feed_object> ret=new ArrayList<>();
        Gson gson = new Gson();
        Feed.request_body temp=new Feed().new request_body(0,"");
        String jsonInString = gson.toJson(temp);
        FeedPOSTRequest postRequest=new FeedPOSTRequest();
        try{
            postRequest.execute("getNewsArticleList",jsonInString,cookie);
            Mlog.e("getinitalnews","success");
        }catch (Exception e){
            Mlog.e("getinitalnews",e.toString());
        }
    }
    public static void getlaternews(String cookie){
        String ret=new String();
        Gson gson = new Gson();
        FeedDb maxid=MainActivity.feedViewModel.getmax();
        Integer max_id=maxid.Id;
        if(maxid==null){
            max_id=new Integer(0);
        }
        Feed.request_body temp;
        if(max_id==0){
            temp=new Feed().new request_body(max_id,"");
        }else{
            temp=new Feed().new request_body(max_id,maxid.Label);
        }
        String jsonInString = gson.toJson(temp);
        FeedPOSTRequest postRequest=new FeedPOSTRequest();
        RequestHelper requestHelper;
        try{
            postRequest.execute("getNewsArticleList",jsonInString,cookie);
            Log.e("Control","post request sent");
        }catch (Exception e){
            Log.e("Control",e.toString());
        }
    }
}
