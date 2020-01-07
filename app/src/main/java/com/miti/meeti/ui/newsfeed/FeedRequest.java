package com.miti.meeti.ui.newsfeed;

import android.util.Log;

import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Feed.FeedObject;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.ArrayList;
import java.util.List;

import static com.miti.meeti.ui.newsfeed.newfeed.cvm;

public class FeedRequest {
    public static void getinitialnews(String cookie){
        List<Feed.feed_object> ret=new ArrayList<>();
        Gson gson = new Gson();
        Feed.request_body temp=new Feed().new request_body(0);
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
        Feed.request_body temp=new Feed().new request_body(FeedViewModel.templkh);
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
