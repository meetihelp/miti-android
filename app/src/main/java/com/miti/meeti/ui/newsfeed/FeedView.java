package com.miti.meeti.ui.newsfeed;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.miti.meeti.MainActivity;
import com.miti.meeti.NetworkObjects.FeedNews;
import com.miti.meeti.R;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.network.RequestHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;

import java.io.InputStream;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedView.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedView extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static View v;
    private WebView browser;
    private String type;
    private Bundle bundle;
    public FeedView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedView.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedView newInstance(String param1, String param2) {
        FeedView fragment = new FeedView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bundle = this.getArguments();
        type=bundle.getString("Flag","none");
        if(type.contains("web")) {
            String url = bundle.getString("ArticeURL");
            v = inflater.inflate(R.layout.fragment_feed_view, container, false);
            final ProgressBar pbar = (ProgressBar) v.findViewById(R.id.progressBar3);
            browser = (WebView) v.findViewById(R.id.webview);
            if (browser != null) {
                Mlog.e("in webview", url);
                WebSettings webSetting = browser.getSettings();
                webSetting.setJavaScriptEnabled(true);
//                webSetting.setBuiltInZoomControls(true);
                browser.setWebChromeClient(new WebChromeClient() {
                    @Override
                    public void onProgressChanged(WebView view, int newProgress) {
                        if (newProgress < 100 && pbar.getVisibility() == ProgressBar.GONE) {
                            pbar.setVisibility(ProgressBar.VISIBLE);
//                            txtview.setVisibility(View.VISIBLE);
                        }
                        pbar.setProgress(newProgress);
                        if (newProgress == 100) {
                            pbar.setVisibility(ProgressBar.GONE);
                        }
                    }

                });
//                webSetting.setForceDark(WebSettings.FORCE_DARK_ON);
                browser.loadUrl(url);
                return v;
            }
            } else if (type.contains("app")) {
                v = inflater.inflate(R.layout.feed_item, container, false);
            }
            return v;

    }
//    private void injectCSS() {
//        Mlog.e("aaya main");
//        try {
//            InputStream inputStream = getActivity().getAssets().open("nightmode.css");
//            byte[] buffer = new byte[inputStream.available()];
//            inputStream.read(buffer);
//            inputStream.close();
//            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
//            browser.loadUrl("javascript:(function() {" +
//                    "var parent = document.getElementsByTagName('head').item(0);" +
//                    "var style = document.createElement('style');" +
//                    "style.type = 'text/css';" +
//                    // Tell the browser to BASE64-decode the string into your script !!!
//                    "style.innerHTML = window.atob('" + encoded + "');" +
//                    "parent.appendChild(style)" +
//                    "})()");
//        } catch (Exception e) {
//            Mlog.e(e);
//        }
//    }

    @Override
    public void onStart(){
        super.onStart();
        if(type.contains("app")){
            TextView temp=(TextView) v.findViewById(R.id.feed_heading);
            temp.setText(bundle.getString("Title"));
            TextView temp1=(TextView) v.findViewById(R.id.feed_text);
            ImageView temp3=(ImageView) v.findViewById(R.id.imageView2);
            Glide.with(newfeed.v.getContext()).load(bundle.getString("ImageURL")).diskCacheStrategy(DiskCacheStrategy.ALL).into(temp3);
            POSTRequest postRequest=new POSTRequest();
            FeedNews.request_body body=new FeedNews().new request_body(bundle.getInt("Id"));
            Gson gson=new Gson();
            String jsonbody=gson.toJson(body);
            try{
                RequestHelper retbody=postRequest.execute("getNewsArticle",jsonbody, MainActivity.cookieViewModel.getCookie1()).get();
                FeedNews.response_object ret=gson.fromJson(retbody.getData(),FeedNews.response_object.class);
                temp1.setText(ret.Response.Article);
            }catch (Exception e){
                Mlog.e("in feedview",e.toString(),"");
            }
        }
    }
//    private void injectScriptFile(String filename) {
//        InputStream input;
//        try {
//            input = getActivity().getAssets().open(filename);
//            byte[] buffer = new byte[input.available()];
//            input.read(buffer);
//            input.close();
//            // String-ify the script byte-array using BASE64 encoding !!!
//            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
//            browser.loadUrl("javascript:(function() {" +
//                    "var parent = document.getElementsByTagName('head').item(0);" +
//                    "var script = document.createElement('script');" +
//                    "script.type = 'text/javascript';" +
//                    // Tell the browser to BASE64-decode the string into your script !!!
//                    "script.innerHTML = window.atob('" + encoded + "');" +
//                    "parent.appendChild(script)" +
//                    "})()");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            Mlog.e(e.toString());
//        }
//    }
    @Override
    public void onDetach() {
        super.onDetach();
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
