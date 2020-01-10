package com.miti.meeti.ui.newsfeed;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.Feed;
import com.miti.meeti.R;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Feed.FeedDb;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.mitiutil.uihelper.EndlessRecyclerViewScrollListener;

import java.util.List;

public class newfeed extends Fragment{
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
    public static KeyvalueViewModel kvm;
    private String cookie;
    public static View progress;
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
        setHasOptionsMenu(true);
        v=inflater.inflate(R.layout.fragment_newfeed, container, false);
        progress=v.findViewById(R.id.progressBar2);
        progress.setVisibility(View.GONE);
        cvm= MainActivity.cookieViewModel;
        kvm=ViewModelProviders.of(this).get(KeyvalueViewModel.class);
        cookie=cvm.getCookie1();
        if(kvm.get("userid")==null){
            GETid.getid(cookie);
        }
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
        feedViewModel.getAll().observe(this, new Observer<List<FeedDb>>() {
            @Override
            public void onChanged(List<FeedDb> feeds) {
                if(feeds.size()==0){
                    FeedRequest.getinitialnews(cookie);
                }else{
                    feedAdapter.setTemp(feeds);
                }
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
        FeedRequest.getlaternews(cookie);
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.autismfont:
                Typeface font = ResourcesCompat.getFont(v.getContext(),R.font.popcorn);
                Typeface font1 = ResourcesCompat.getFont(v.getContext(),R.font.pacifico);
                TextView tv1=v.findViewById(R.id.feed_heading);
                TextView tv2=v.findViewById(R.id.feed_text);
                tv1.setTypeface(font1);
                tv2.setTypeface(font);
                Drawable drawable = item.getIcon();
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable, ContextCompat.getColor(v.getContext(),R.color.mitiOrange));
                item.setIcon(drawable);
            case R.id.actionview_messages:
                Navigation.findNavController(v).navigate(R.id.message_list);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
