package com.miti.meeti.ui.newsfeed;

import android.util.Log;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;

public class FeedPOSTRequest extends POSTRequest {

    @Override
    protected void onPostExecute(RequestHelper result) {
//        super.onPostExecute(result);
        Gson gson = new Gson();
        try{
            Feed.response_object tempqw=gson.fromJson(result.getData(),Feed.response_object.class);
            newfeed.feedViewModel.addTodo(tempqw.NewsData);
            FeedViewModel.templkh=tempqw.NewsData.get(0).Id+1;
        }catch (Exception e){

        }
        Log.e("Control","Recycler view changed");
    }
}
