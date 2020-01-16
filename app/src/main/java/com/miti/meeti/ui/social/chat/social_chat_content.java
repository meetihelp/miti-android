package com.miti.meeti.ui.social.chat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Chat.ChatDbViewModel;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.mitiutil.uihelper.MitiDiff;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;
import com.miti.meeti.mitiutil.uihelper.ImageSaver;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class social_chat_content extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String chatid;
    private static List<ChatDb>allchatsynchronized;
    private List<ChatDb>messagesold;
    public static String requestid;
    public static String content;
    public static MessagesListAdapter<Message> adapterx;
    public static ChatDbViewModel chatDbViewModel;
    public static String userid;
    public static KeyvalueViewModel keyvalueViewModel;
    public static String cookie;
    private View v;
    public static Boolean mitilock;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Uri>mSelected;

    public social_chat_content() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment social_chat_content.
     */
    // TODO: Rename and change types and number of parameters
    public static social_chat_content newInstance(String param1, String param2) {
        social_chat_content fragment = new social_chat_content();
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
        CookieViewModel cookieViewModel=ViewModelProviders.of(this).get(CookieViewModel.class);
        keyvalueViewModel=ViewModelProviders.of(this).get(KeyvalueViewModel.class);
        cookie=cookieViewModel.getCookie1();
        userid=keyvalueViewModel.get("userid").mitivalue;
        Mlog.e("userid",userid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mitilock=true;
        chatid=getArguments().getString("chatid");
        v=inflater.inflate(R.layout.fragment_social_chat_content, container, false);
        chatDbViewModel= ViewModelProviders.of(this).get(ChatDbViewModel.class);

        final Observer<List<ChatDb>> nameObserver = new Observer<List<ChatDb>>() {
            @Override
            public void onChanged(@Nullable final List<ChatDb> newName) {
                onChanged1(newName);
            }
        };
        chatDbViewModel.getchatbyid(chatid).observe(this,nameObserver);
        chatDbViewModel.getmax(chatid);
        LiveData<List<ChatDb>>lkjh=chatDbViewModel.getchatbyid(chatid);
        MainActivity.SetNavigationVisibiltity(false);
        MessageInput inputView=v.findViewById(R.id.input);
        inputView.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() {
                Matisse.from(social_chat_content.this)
                        .choose(MimeType.ofAll())
                        .countable(true)
                        .maxSelectable(9)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(false)
                        .theme(R.style.Matisse_Dracula)
                        .forResult(10); // Default is `true
            }
        });
        MessagesList inputlist=v.findViewById(R.id.messagesList);
        System.out.println("Aaya me - Apoorva");
        Author temp=new Author("","apoorva kumar","");
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                Glide.with(social_chat_content.this).load(url).into(imageView);
            }
        };
        adapterx = new MessagesListAdapter<>(userid, imageLoader);
        inputlist.setAdapter(adapterx);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                if(mitilock){
                    Author temp=new Author("","apoorva kumar","");
                    String mitidt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
                    requestid=UUID.randomUUID().toString().replace("-","").substring(0,32);
                    chatDbViewModel.insertnew(new ChatDb(userid,"text",input.toString(),requestid,chatid,mitidt,-1));
                    content=input.toString();
//                    adapterx.addToStart(tempx, true);
                    return true;
                }else{
                    ToastHelper.ToastFun(v.getContext(),"Try again");
                    return false;
                }
            }
        });
        return v;
    }

    public static synchronized void setall(List<ChatDb> allchat){
        allchatsynchronized=allchat;
    }
    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onDetach() {
        super.onDetach();
        allchatsynchronized = null;
    }

    public static synchronized void onChanged1(@Nullable final List<ChatDb>messages) {
        Mlog.e("callback","onchanged in message content",Integer.toString(messages.size()));
        if(messages.size()==0){
            return;
        }
        if(allchatsynchronized==null){
            setall(messages);
            addinmessagelist(messages);
        }else{
            List<ChatDb>diff= new MitiDiff<String,ChatDb>().getx(allchatsynchronized,messages,"RequestId");
            Mlog.e("I am going to add");
            addinmessagelist(diff);
            setall(messages);
            Mlog.e("added not being called");
        }
//        Set<ChatDb> ad = new HashSet<ChatDb>(messages);
//        Set<ChatDb> ad1 = new HashSet<ChatDb>(messagesold);
//        ad.removeAll(ad1);
//        messages.clear();
//        messages.addAll(ad);
    }
    public static synchronized void addinmessagelist(@Nullable final List<ChatDb>messages){
//        for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
//            Log.e("control",ste.toString());
//        }
        Mlog.e("Inserting in addinmessagelist",Integer.toString(messages.size()));
        if(messages==null){
            return;
        }
        List<Message>temp12=new ArrayList<>();
        for (ChatDb tempx:messages){
            Author temp45=new Author(tempx.UserId,tempx.UserId,"");
//            Mlog.e(tempx.UserId,tempx.MessageContent);
            String date=new String();
            if(tempx.CreatedAt!=null){
                date=tempx.CreatedAt;
            }else{
                date= try123.mitidt();
            }
            Message temp34=new Message(tempx.MessageId,tempx.MessageContent,temp45,date);
            if(tempx.MessageType.contains("image")){
                temp34.setUrl(tempx.ImageUrl);
            }
            temp12.add(temp34);
        }
        social_chat_content.adapterx.addToEnd(temp12,false);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.e("Matisse", "mSelected: " + Matisse.obtainPathResult(data));
            List<String> templ = Matisse.obtainPathResult(data);
            new ChatSaveImage().execute("ChatImages","Chats",templ.get(0));
        }
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

}
