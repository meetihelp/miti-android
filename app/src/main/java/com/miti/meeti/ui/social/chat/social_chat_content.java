package com.miti.meeti.ui.social.chat;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
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
import com.miti.meeti.ui.privacy.PrivacyFragment;
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

public class social_chat_content extends Fragment implements MessagesListAdapter.OnMessageClickListener<Message>,
        MessagesListAdapter.OnMessageLongClickListener<Message>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String chatid;
    private static List<ChatDb>allchatsynchronized=new ArrayList<>();
    private List<ChatDb>messagesold;
    public static String requestid;
    public static String content;
    public static MessagesListAdapter<Message> adapterx;
    public static ChatDbViewModel chatDbViewModel;
    public static String userid;
    private static ActionModeCallback actionModeCallback;
    public static Toolbar toolbar=MainActivity.toolbar;
    private static ActionMode actionMode;
    public static KeyvalueViewModel keyvalueViewModel;
    public static String cookie;
    private Message selected;
    public static FragmentActivity myContext;
    private View v;
    public static Boolean mitilock;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    List<Uri>mSelected;
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.moodboard_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public void onMessageClick(Message message) {
            Bundle args = new Bundle();
            args.putString("resourceid", message.type);
            if(message.type.contains("text")){
                args.putString("data", message.getText());
            }else{
                args.putString("data", message.getImageUrl());
            }
            MyDialogFragment bottomSheetDialog = MyDialogFragment.newInstance();
            bottomSheetDialog.setArguments(args);
            bottomSheetDialog.show(myContext.getSupportFragmentManager(),"hithere");
    }

    @Override
    public void onMessageLongClick(Message message) {
        if(selected==null){
            enableActionMode();
            selected=message;
        }else{
            if(selected.getId().contains(message.getId())){
                reset();
            }else{
                selected=message;
            }
        }
    }


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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        myContext=(FragmentActivity)context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        CookieViewModel cookieViewModel=ViewModelProviders.of(this).get(CookieViewModel.class);
        keyvalueViewModel=ViewModelProviders.of(this).get(KeyvalueViewModel.class);
        actionModeCallback=new ActionModeCallback();
        cookie=cookieViewModel.getCookie1();
        userid=keyvalueViewModel.get("userid").mitivalue;
        Mlog.e("userid",userid);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mitilock=true;
        Bundle bundle=getArguments();
        chatid=bundle.getString("chatid");
        String tempid=bundle.getString("apnaid");
        if(userid==null){
            userid=tempid;
        }
        if(tempid!=null){
            if(tempid.contains(userid)){
                Mlog.e("social_chat_content","donoequalhaibro");
            }
        }

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
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(true)
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
        adapterx.setOnMessageClickListener(this);
        adapterx.setOnMessageLongClickListener(this);
        inputlist.setAdapter(adapterx);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                if(mitilock){
                    //String userid, String chatid,String input
                    ChatDb chatDb=chatdbhelper(userid,chatid,input.toString());
                    Message temp34=chathelper(chatDb);
                    add(chatDb);
                    chatDbViewModel.insertnew(chatDb);
                    content=input.toString();
                    return true;
                }else{
                    ToastHelper.ToastFun(v.getContext(),"Try again");
                    return false;
                }
            }
        });
        return v;
    }

    public static synchronized void add(ChatDb temp){
        allchatsynchronized.add(temp);
    }
    public static synchronized void setall(List<ChatDb> allchat){
        allchatsynchronized=allchat;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public static ChatDb chatdbhelper(String userid1, String chatid1,String input){
        Author temp=new Author(userid1,"","");
        String mitidt=try123.mitidt();
        requestid=UUID.randomUUID().toString().replace("-","").substring(0,16);
        ChatDb chatDb=new ChatDb(userid1,"text",input,requestid,chatid1,mitidt,-1);
        return chatDb;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        allchatsynchronized.clear();
//        reset();
    }

    public static synchronized void onChanged1(@Nullable final List<ChatDb>messages) {
        Mlog.e("callback","onchanged in message content",Integer.toString(messages.size()));
        adapterx.clear();
        addinmessagelist(messages);
//        if(messages.size()==0){
//            return;
//        }
//        if(allchatsynchronized==null || allchatsynchronized.size()==0){
//            setall(messages);
//            addinmessagelist(messages);
//        }else{
//            List<ChatDb>diff= new MitiDiff<String,ChatDb>().getx(allchatsynchronized,messages,"RequestId");
//            Mlog.e("I am going to add");
//            addinmessagelist(diff);
//            setall(messages);
//        }
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
            Message temp34=chathelper(tempx);
            temp12.add(temp34);
        }
        adapterx.addToEnd(temp12,false);
    }
    public static Message chathelper(ChatDb tempx){
        Author temp45=new Author(tempx.UserId,tempx.UserId,"");
//            Mlog.e(tempx.UserId,tempx.MessageContent);
        String date=new String();
        if(tempx.CreatedAt!=null){
            date=tempx.CreatedAt;
        }else{
            date= tempx.UserCreatedAt;
        }
        Message temp34=new Message(tempx.MessageId,tempx.MessageContent,temp45,date,tempx.MessageType);
        if(tempx.MessageType.contains("image")){
            temp34.setUrl(tempx.ImageUrl);
        }
        return temp34;
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
    public static class MyDialogFragment extends DialogFragment {
        private View v;
        private static final int INVALID_POINTER_ID = -1;
        static MyDialogFragment newInstance() {
            MyDialogFragment f = new MyDialogFragment();
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.moodboard_dialog, container, false);
            Bundle mArgs = getArguments();
            String myValue = mArgs.getString("resourceid");
            v.findViewById(R.id.delete_mood_object).setVisibility(View.GONE);
            if(myValue.contains("image")){
                Glide.with(v.getContext()).load(mArgs.getString("data")).into((PhotoView) v.findViewById(R.id.moodboard_image));
            }else if(myValue.contains("text")){
                TextView temp=(TextView) v.findViewById(R.id.moodboard_content);
                temp.setText(mArgs.getString("data"));
            }
            return v;
        }

    }
    public static void enableActionMode() {
        Mlog.e("enablecalled");
        actionMode=toolbar.startActionMode(actionModeCallback);
//        toggleSelection(position);
    }
    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            Log.d("API123", "here");
            switch (item.getItemId()) {
                case R.id.action_copy:
                    // delete all the selected rows
                    if (selected.type.contains("image")) {
                        ToastHelper.ToastFun(myContext, "App isn't that talented to copy image");
                    } else if (selected.type.contains("text")) {
                        ClipboardManager clipboard = (ClipboardManager) myContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("MEETi", selected.getText());
                        clipboard.setPrimaryClip(clip);
                        ToastHelper.ToastFun(myContext, "You beautiful human, Your content has been copied");
                    }
                    reset();
                    return true;

                case R.id.action_delete:
                    // delete all the selected rows
                    return true;

                case R.id.action_share:
                    if(selected==null){
                        ToastHelper.ToastFun(myContext,"null");
                        return true;
                    }
                    Bundle bundle = new Bundle();
                    String type;
                    String contentx = new String();
                    bundle.putString("type", selected.type);
                    if (selected.type.contains("text")) {
                        contentx = selected.getText();
                    } else if (selected.type.contains("image")) {
                        contentx = selected.getImageUrl();
                    }
                    bundle.putString("content", contentx);
                    bundle.putString("from", "mood");
                    Navigation.findNavController(v).navigate(R.id.action_move_to_newMessage, bundle);
                    Mlog.e("share called");
                    return true;

                default:
                    return false;
            }
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
//            mAdapter.clearSelections();
            reset();
        }
    }
    public void reset(){
        selected=null;
        actionMode.finish();
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
