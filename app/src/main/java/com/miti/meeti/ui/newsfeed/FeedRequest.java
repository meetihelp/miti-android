package com.miti.meeti.ui.newsfeed;

import android.util.Log;

import androidx.lifecycle.ViewModelProviders;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Feed.FeedObject;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

import java.util.ArrayList;
import java.util.List;

import static com.miti.meeti.ui.newsfeed.newfeed.cvm;

public class FeedRequest {
    public static List<Feed.feed_object> getinitialnews(){
        List<Feed.feed_object> ret=new ArrayList<>();
        Gson gson = new Gson();
        Feed.request_body temp=new Feed().new request_body(0);
        String jsonInString = gson.toJson(temp);
        POSTRequest postRequest=new POSTRequest();
        RequestHelper requestHelper;
        try{
            requestHelper= postRequest.execute("getNewsArticleList",jsonInString,cvm.getCookie1()).get();
            String result=requestHelper.getData();
            Feed.response_object tempqw=gson.fromJson(result,Feed.response_object.class);
            ret.add(tempqw.NewsData.get(0));
            ret.add(tempqw.NewsData.get(1));
            Log.e("Control","returned");
            return ret;
        }catch (Exception e){
            Log.e("Control",e.toString());
        }
        return ret;
    }
    public static void getlaternews(){
        String ret=new String();
        Gson gson = new Gson();
        Feed.request_body temp=new Feed().new request_body(FeedViewModel.templkh);
        String jsonInString = gson.toJson(temp);
        FeedPOSTRequest postRequest=new FeedPOSTRequest();
        RequestHelper requestHelper;
        try{
            postRequest.execute("getNewsArticleList",jsonInString,"558eca4e-0475-4164-47e5-a720a4b55119").get();
            Log.e("Control","post request sent");
        }catch (Exception e){
            Log.e("Control",e.toString());
        }
    }
}
