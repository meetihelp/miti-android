package com.miti.meeti;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miti.meeti.bottomnav.CurvedBottomNavigationView;

import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.miti.meeti.database.Chat.ChatListDbViewModel;
import com.miti.meeti.database.Cookie.Cookie;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Diary.MoodboardViewModel;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.uihelper.PermissionHelper;
import com.zhihu.matisse.Matisse;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private AppBarConfiguration mAppBarConfiguration;
    private AppBarConfiguration mAppBarConfiguration1;
    private static BottomNavigationView bottomNavigationView;
    private NavController navController;
    public static MoodboardViewModel moodboardViewModel;
    public static CookieViewModel cookieViewModel;
    public static FeedViewModel feedViewModel;
    public static ChatListDbViewModel chatListDbViewModel;
    private static AppBarLayout appBarLayout;
    public static String RootFolder;
    public static Context MainActivityContext;
    public static void SetNavigationVisibiltity (boolean b) {
        if (b) {
            bottomNavigationView.setVisibility(View.VISIBLE);
//            AlphaAnimation animation1 = new AlphaAnimation(bottomNavigationView.getAlpha(), 1.0f);
//            animation1.setDuration(200);
//            animation1.setFillAfter(true);
//            bottomNavigationView.startAnimation(animation1);

        } else {
//            appBarLayout.setVisibility(View.GONE);
            bottomNavigationView.setVisibility(View.GONE);
//            AlphaAnimation animation1 = new AlphaAnimation(bottomNavigationView.getAlpha(), 0.0f);
//            animation1.setDuration(200);
//            animation1.setFillAfter(true);
//            bottomNavigationView.startAnimation(animation1);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean temp=PermissionHelper.PermissionGranted(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},this);
        if(temp){
            setup();
        }
        moodboardViewModel=ViewModelProviders.of(this).get(MoodboardViewModel.class);
        cookieViewModel=ViewModelProviders.of(this).get(CookieViewModel.class);
        feedViewModel=ViewModelProviders.of(this).get(FeedViewModel.class);
        chatListDbViewModel=ViewModelProviders.of(this).get(ChatListDbViewModel.class);
        MainActivityContext=this;
//        BottomAppBar curvedBottomNavigationView = findViewById(R.id.bar);
//        curvedBottomNavigationView.inflateMenu(R.menu.activity_main_drawer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i] == PackageManager.PERMISSION_GRANTED){
                Log.v("Control","Permission: "+permissions[i]+ "was "+grantResults[i]);
            }
        }
        setup();
    }

    public void setup(){
        File folder = new File(Environment.getExternalStorageDirectory() +
                File.separator + "MEETi");
        RootFolder=folder.getPath();
        Mlog.e("MEETi",RootFolder);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }else{
            Mlog.e("MEETi folder already present");
        }
        if (success) {
            Mlog.e("folder created");
        } else {
            Mlog.e("folder creation failed");
        }
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                 R.id.miti_newsfeed, R.id.miti_social,
//                R.id.miti_security, R.id.miti_privacy, R.id.miti_utility)
//                .setDrawerLayout(drawer)
//                .build();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
//        NavigationUI.setupActionBarWithNavController(this,navController);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
//        CurvedBottomNavigationView curvedBottomNavigationView = findViewById(R.id.customBottomBar);
//        curvedBottomNavigationView.inflateMenu(R.menu.activity_main_drawer1);
        bottomNavigationView = findViewById(R.id.customBottomBar);
        bottomNavigationView.inflateMenu(R.menu.activity_main_drawer1);
        NavigationUI.setupWithNavController(bottomNavigationView,
                navController);

    }
}

//        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });