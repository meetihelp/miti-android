package com.miti.meeti.ui.newsfeed;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.R;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.mitiutil.uihelper.EndlessRecyclerViewScrollListener;

import java.util.List;

import static com.miti.meeti.mitiutil.try123.randomAlphaNumeric;

public class newfeed extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static View v;
    private  RecyclerView recyclerView;
    static public FeedViewModel feedViewModel;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Handler mWaitHandler = new Handler();
    private OnFragmentInteractionListener mListener;
    boolean isLoading = false;
    public static CookieViewModel cvm;
    public newfeed() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v=inflater.inflate(R.layout.fragment_newfeed, container, false);
        cvm=ViewModelProviders.of(this).get(CookieViewModel.class);
        recyclerView=v.findViewById(R.id.feed_recyclerview);
        LinearLayoutManager llm=new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(llm);
        final FeedAdapter feedAdapter = new FeedAdapter();
        scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
        recyclerView.setAdapter(feedAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(2);
        feedViewModel= ViewModelProviders.of(this).get(FeedViewModel.class);
        feedViewModel.getTodos().observe(this, new Observer<List<Feed.feed_object>>() {
            @Override
            public void onChanged(List<Feed.feed_object> feeds) {
                feedAdapter.setTemp(feeds);
            }
        });
        return v;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void loadNextDataFromApi(int offset) {
        Log.e("Control","loadnextmeaayamain");
        FeedRequest.getlaternews();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
