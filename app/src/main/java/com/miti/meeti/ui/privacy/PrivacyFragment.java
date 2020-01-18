package com.miti.meeti.ui.privacy;

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
import android.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.database.Diary.MoodboardViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.mitiutil.uihelper.ImageSaver;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PrivacyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PrivacyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrivacyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static boolean isInActionMode = false;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public static View v;
    private List<Uri> mSelected;
//    private OnFragmentInteractionListener mListener;
    public static Toolbar toolbar=MainActivity.toolbar;
    public static ArrayList<Moodboard> selectionList = new ArrayList<>();
    private MoodboardViewModel moodboardViewModel;
    private EditText text;
    private FloatingActionButton EditButton;
    private FloatingActionButton FabPic;
    private FloatingActionButton FabCamera;
    private LinearLayout layoutFabEdit;
    private LinearLayout layoutFabImage;
    private LinearLayout layoutFabCam;
    private LinearLayout temp;
    private static ActionModeCallback actionModeCallback;
    private static ActionMode actionMode;
    private List<Moodboard>allxy;
    private LiveData<List<Moodboard>>all;
    private ImageButton save;
    private ImageButton close;
    private boolean fabExpanded = false;
    private FloatingActionButton fabSettings;
    private StaggeredGridLayoutManager _sGridLayoutManager;
    private MoodboardAdapter moodboardAdapter;
    public static FragmentActivity myContext;
    public PrivacyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PrivacyFragment newInstance(String param1, String param2) {
        PrivacyFragment fragment = new PrivacyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        myContext=(FragmentActivity) context;
        super.onAttach(context);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.moodboard_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        actionModeCallback = new ActionModeCallback();
        MainActivity.toolbar_text.setText("MoodBoards");
        setHasOptionsMenu(true);
        v=inflater.inflate(R.layout.fragment_moodboard, container, false);
        FabCamera = v.findViewById(R.id.fabCam);
        FabCamera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_miti_privacy_to_camera_moodboard);
            }
        });

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.moodboard_recycler);
        recyclerView.setHasFixedSize(true);
        moodboardAdapter=new MoodboardAdapter();
        _sGridLayoutManager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(_sGridLayoutManager);
        moodboardAdapter.setHasStableIds(true);
        recyclerView.setAdapter(moodboardAdapter);
        moodboardViewModel= MainActivity.moodboardViewModel;
        all=moodboardViewModel.getAll();
        temp=v.findViewById(R.id.moodbard_input_text);
        text=v.findViewById(R.id.moodboard_edittext);
        save=v.findViewById(R.id.save_mood);
        close=v.findViewById(R.id.close_mood);
        final Observer<List<Moodboard>> nameObserver = new Observer<List<Moodboard>>() {
            @Override
            public void onChanged(@Nullable final List<Moodboard> newName) {
                allxy=newName;
                moodboardAdapter.setTemp(newName);
            }
        };
        all.observe(this,nameObserver);
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                String BoardId,String requestId,String userCreatedAt,String mimetype,String content,String image_path,int sync
                moodboardViewModel.insert(new Moodboard(try123.mitidt(),try123.randomAlphaNumeric(32),try123.mitidt(),
                        "text",text.getText().toString(),null,-1));
                text.setText("");
                temp.setVisibility(View.GONE);
            }
        });
        close.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                text.setText("");
                temp.setVisibility(View.GONE);
            }
        });
        temp.setVisibility(View.GONE);
        Mlog.e("SET invisible");
        EditButton=(FloatingActionButton)v.findViewById(R.id.fabEdit);
        FabPic=(FloatingActionButton)v.findViewById(R.id.fabPic);
        layoutFabEdit = (LinearLayout) v.findViewById(R.id.layoutFabEdit);
        layoutFabImage = (LinearLayout) v.findViewById(R.id.layoutFabPic);
        layoutFabCam = (LinearLayout) v.findViewById(R.id.layoutFabCam);
        fabSettings = (FloatingActionButton) v.findViewById(R.id.fabSetting);
        FabPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSubMenusFab();
                Matisse.from(PrivacyFragment.this)
                        .choose(MimeType.ofImage())
                        .countable(true)
                        .maxSelectable(1)
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .showPreview(false)
                        .theme(R.style.Matisse_Dracula)
                        .forResult(10); // Default is `true
            }
        });
        fabSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fabExpanded == true){
                    closeSubMenusFab();
                } else {
                    openSubMenusFab();
                }
            }
        });
        closeSubMenusFab();
        MainActivity.SetNavigationVisibiltity(false);
        EditButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Mlog.e(temp.toString());
                temp.setVisibility(View.VISIBLE);
                closeSubMenusFab();
            }
        });
        return v;
    }
    private void closeSubMenusFab(){
        layoutFabCam.setVisibility(View.INVISIBLE);
        layoutFabEdit.setVisibility(View.INVISIBLE);
        layoutFabImage.setVisibility(View.INVISIBLE);
        fabSettings.setImageResource(R.drawable.ic_add_black_24dp);
        fabExpanded = false;
    }

    //Opens FAB submenus
    private void openSubMenusFab(){
        layoutFabCam.setVisibility(View.VISIBLE);
        layoutFabEdit.setVisibility(View.VISIBLE);
        layoutFabImage.setVisibility(View.VISIBLE);
        //Change settings icon to 'X' icon
        fabSettings.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        moodboardAdapter.selected=-1;
        if(actionMode!=null){
            actionMode.finish();
        }
        MainActivity.SetNavigationVisibiltity(true);
        MainActivity.toolbar_text.setText("Meeti");
//        mListener = null;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Log.e("Matisse", "mSelected: " + Matisse.obtainPathResult(data));
            List<String>templ=Matisse.obtainPathResult(data);
            new BoardImageSaver().execute("Moodboards","Moodboard",templ.get(0));
        }
    }
    public static void enableActionMode(int position) {
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
                    Moodboard content=allxy.get(moodboardAdapter.position);
                    if(content.Mimetype.contains("image")){
                        ToastHelper.ToastFun(myContext,"App isn't that talented to copy image");
                    }else if(content.Mimetype.contains("text")){
                        ClipboardManager clipboard = (ClipboardManager) myContext.getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData clip = ClipData.newPlainText("MEETi",allxy.get(moodboardAdapter.position).Content);
                        clipboard.setPrimaryClip(clip);
                        ToastHelper.ToastFun(myContext,"You beautiful human, Your content has been copied");
                    }
                    reset();
                    return true;

                case R.id.action_delete:
                    // delete all the selected rows
                    moodboardViewModel.delete(allxy.get(moodboardAdapter.position));
                    reset();
                    Mlog.e("delete called");
                    return true;

                case R.id.action_share:
                    reset();
                    Navigation.findNavController(v).navigate(R.id.action_move_to_newMessage);
                    Mlog.e("share called");
                    return true;

                default:
                    return false;
            }
        }
        public void reset(){
            moodboardAdapter.selected=-1;
            moodboardAdapter.notifyDataSetChanged();
            actionMode.finish();
        }
        @Override
        public void onDestroyActionMode(ActionMode mode) {
//            mAdapter.clearSelections();
            reset();
        }
    }
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
