package com.miti.meeti.ui.newsfeed;

import android.view.View;

import com.google.gson.Gson;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.R;
import com.miti.meeti.database.Feed.FeedDb;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.try123;

import java.util.ArrayList;
import java.util.List;

import static com.miti.meeti.ui.newsfeed.newfeed.progress;
import static com.miti.meeti.ui.newsfeed.newfeed.swipeRefreshLayout;

public class FeedPOSTRequest extends POSTRequest {
    @Override
    protected void onPreExecute() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(RequestHelper result) {
        if(result==null){
            return;
        }
//        super.onPostExecute(result);
        Gson gson = new Gson();
        try{
            Feed.response_object tempqw=gson.fromJson(result.getData(), Feed.response_object.class);
            if(tempqw==null){return;}
            List<FeedDb>temp=new ArrayList<>();
            for(FeedDb tempx:tempqw.NewsData){
                tempx.UserCreatedAt= try123.mitidt();
                temp.add(tempx);
            }
            newfeed.feedViewModel.insert(temp.toArray(new FeedDb[temp.size()]));
        }catch (Exception e){

        }
        swipeRefreshLayout.setRefreshing(false);
        Mlog.e("Recycler view changed");
    }
}
