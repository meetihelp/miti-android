package com.miti.meeti;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.miti.meeti.MitiExecutors.MitiRunnables.UpdateChatMessages;
import com.miti.meeti.MitiExecutors.MitiRunnables.UpdateChatlist;
import com.miti.meeti.MitiExecutors.MitiService;
import com.miti.meeti.NetworkObjects.Mitigps;
import com.miti.meeti.bottomnav.CurvedBottomNavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Chat.ChatDbViewModel;
import com.miti.meeti.database.Chat.ChatListDbViewModel;
import com.miti.meeti.database.Cookie.Cookie;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Diary.MoodboardViewModel;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.Keyvalue;
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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private AppBarConfiguration mAppBarConfiguration;
    private AppBarConfiguration mAppBarConfiguration1;
    private static BottomNavigationView bottomNavigationView;
    private NavController navController;
    public static MoodboardViewModel moodboardViewModel;
    public static CookieViewModel cookieViewModel;
    public static KeyvalueViewModel keyvalueViewModel;
    public static FeedViewModel feedViewModel;
    public static ChatListDbViewModel chatListDbViewModel;
    public static ChatDbViewModel chatDbViewModel;
    private static AppBarLayout appBarLayout;
    private LocationManager locationManager;
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
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS,
                ACCESS_FINE_LOCATION},this);
        keyvalueViewModel=ViewModelProviders.of(this).get(KeyvalueViewModel.class);
        moodboardViewModel=ViewModelProviders.of(this).get(MoodboardViewModel.class);
        cookieViewModel=ViewModelProviders.of(this).get(CookieViewModel.class);
        feedViewModel=ViewModelProviders.of(this).get(FeedViewModel.class);
        chatListDbViewModel=ViewModelProviders.of(this).get(ChatListDbViewModel.class);
        chatDbViewModel=ViewModelProviders.of(this).get(ChatDbViewModel.class);
        MainActivityContext=this;
//        MitiService mitiService=new MitiService(1);
//        mitiService.schedule(new UpdateChatlist(),0,60, TimeUnit.SECONDS);
//        mitiService.schedule(new UpdateChatMessages(),0,60, TimeUnit.SECONDS);
        if(temp){
            setup();
        }

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
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            Location loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if(loc!=null){
                Mlog.e("loc not null");
                Mitigps gps=new Mitigps(new Double(loc.getLatitude()).toString(),new Double(loc.getLongitude()).toString());
                keyvalue kv=new keyvalue("gps",new Gson().toJson(gps));

                keyvalueViewModel.insert(kv);
            }else{
                Mlog.e("loc null");
            }

        }catch (SecurityException e){
            Mlog.e(e);
        }

//        try{
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 600, 0, this);
//        }catch (SecurityException e){
//            Mlog.e(e);
//            Mlog.e("Security Exception");
//        }


//        getLocationRequest();
    }
    @Override
    public void onLocationChanged(Location location) {
        if(location==null){
            Mlog.e("Apoorva location null");
            return;
        }
        Mlog.e("","Apoorva Location",new Double(location.getLatitude()).toString());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    private void showGPSDisabledAlertToUser(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
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