package com.miti.meeti.ui.newsfeed;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.uihelper.EndlessRecyclerViewScrollListener;
import com.miti.meeti.mitiutil.uihelper.InfoDialog;

import java.util.List;

//import me.msfjarvis.apprate.AppRate;

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
    public static Boolean autism=false;
    public static CookieViewModel cvm;
    public static KeyvalueViewModel kvm;
    public static FragmentActivity myContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext=(FragmentActivity)context;
    }

    private String cookie;
    public static SwipeRefreshLayout swipeRefreshLayout;
    private FeedAdapter feedAdapter;
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
//        new AppRate(myContext)
//                .setMinDaysUntilPrompt(7)
//                .setMinLaunchesUntilPrompt(10)
//                .setShowIfAppHasCrashed(true)
//                .init();
        setHasOptionsMenu(true);
        v=inflater.inflate(R.layout.fragment_newfeed, container, false);
        swipeRefreshLayout=v.findViewById(R.id.newfeed_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadNextDataFromApi();
            }
        });
        progress=v.findViewById(R.id.progressBar2);
        progress.setVisibility(View.GONE);
        cvm= MainActivity.cookieViewModel;
        kvm=ViewModelProviders.of(this).get(KeyvalueViewModel.class);
        if(MainActivity.firsttime==null){
            help();
        }
        cookie=MainActivity.MeetiCookie;
        if(kvm.get("userid")==null){
            GETid.getid(cookie);
        }
        recyclerView=v.findViewById(R.id.feed_recyclerview);
        LinearLayoutManager llm=new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(llm);
        feedAdapter = new FeedAdapter();
        scrollListener = new EndlessRecyclerViewScrollListener(llm) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
//                loadNextDataFromApi(page);
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
    public void loadNextDataFromApi() {
        Mlog.e("Control","loadnextmeaayamain");
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
    public void help(){
        String help="Click on the shield button on top left to add people in your trust group to send location\n\n";
        String help1="Click on the red button four times within three seconds to send location to your trust group\n\n";
        String help2="Click on social button in bottom left to get a date or a new group of friends near you.\n\n";
        String help3="Click on Moods button in bottom right to archive your current mood.\n\n";
        String help4="Drag down to get new articles.";
        InfoDialog inf=new InfoDialog(help+help1+help2+help3+help4);
        inf.show(myContext.getSupportFragmentManager(),"hithere");
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.autismfont:
                if(!autism){
                    Drawable drawable = item.getIcon();
                    drawable = DrawableCompat.wrap(drawable);
                    DrawableCompat.setTint(drawable, ContextCompat.getColor(v.getContext(),R.color.mitiOrange));
                    item.setIcon(drawable);
                }else{
                    Drawable drawable = item.getIcon().mutate();
                    drawable.setColorFilter(null);
                    item.setIcon(drawable);
                }
                autism=!autism;
                feedAdapter.notifyDataSetChanged();
                return true;
            case R.id.actionview_miti_utility:
                Navigation.findNavController(v).navigate(R.id.action_move_to_utility);
                return true;
            case R.id.actionview_miti_security:
                Navigation.findNavController(v).navigate(R.id.action_move_to_security);
                return true;
            case R.id.help:
                help();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
