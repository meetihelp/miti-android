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

import static com.miti.meeti.ui.newsfeed.newfeed.progress;

public class FeedPOSTRequest extends POSTRequest {
    @Override
    protected void onPreExecute() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(RequestHelper result) {
//        super.onPostExecute(result);
        Gson gson = new Gson();
        try{
            Feed.response_object tempqw=gson.fromJson(result.getData(), Feed.response_object.class);
            newfeed.feedViewModel.insert(tempqw.NewsData.toArray(new FeedDb[tempqw.NewsData.size()]));
        }catch (Exception e){

        }
        progress.setVisibility(View.GONE);
        Mlog.e("Recycler view changed");
    }
}
