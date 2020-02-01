package com.miti.meeti;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
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
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.miti.meeti.MitiExecutors.MitiRunnables.ChatSync;
import com.miti.meeti.MitiExecutors.MitiRunnables.CleanJobs.FeedClean;
import com.miti.meeti.MitiExecutors.MitiRunnables.DiarySync;
import com.miti.meeti.MitiExecutors.MitiRunnables.DownloadChatImage;
import com.miti.meeti.MitiExecutors.MitiRunnables.GetMessageRequestSync;
import com.miti.meeti.MitiExecutors.MitiRunnables.MessageRequestSync;
import com.miti.meeti.MitiExecutors.MitiRunnables.SecuritySync;
import com.miti.meeti.MitiExecutors.MitiRunnables.UpdateChatMessages;
import com.miti.meeti.MitiExecutors.MitiRunnables.UpdateChatlist;
import com.miti.meeti.MitiExecutors.MitiService;
import com.miti.meeti.NetworkObjects.Mitigps;
import com.miti.meeti.apicompat.mitihelper;
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
import com.miti.meeti.database.Contact.ContactDbViewModel;
import com.miti.meeti.database.Cookie.Cookie;
import com.miti.meeti.database.Cookie.CookieViewModel;
import com.miti.meeti.database.Diary.MoodboardViewModel;
import com.miti.meeti.database.Feed.FeedViewModel;
import com.miti.meeti.database.Keyvalue.KeyvalueViewModel;
import com.miti.meeti.database.Keyvalue.keyvalue;
import com.miti.meeti.database.Request.MessageRqViewModel;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.network.Keyvalue;
import com.miti.meeti.mitiutil.network.POSTRequest;
import com.miti.meeti.mitiutil.uihelper.PermissionHelper;
import com.miti.meeti.mitiutil.uihelper.ToastHelper;
import com.miti.meeti.ui.security.AlertPOST;
import com.zhihu.matisse.Matisse;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import mumayank.com.airlocationlibrary.AirLocation;

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
    public static MessageRqViewModel messageRqViewModel;
    public static Toolbar toolbar;
    public static int no_of_clicks=0;
    public static ContactDbViewModel contactDbViewModel;
    private static AppBarLayout appBarLayout;
    public static String RootFolder;
    public static String MeetiCookie;
    public static String Latitude;
    public static String Longitude;
    public static keyvalue firsttime;
    public FloatingActionButton fabx;
    public LocationManager locationManager;
    public static Context MainActivityContext;
    public static TextView toolbar_text;
    private Handler mHandler;
    private int mInterval = 50000;
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
        contactDbViewModel=ViewModelProviders.of(this).get(ContactDbViewModel.class);
        messageRqViewModel=ViewModelProviders.of(this).get(MessageRqViewModel.class);
        MeetiCookie=cookieViewModel.getCookie1();
        MainActivityContext=this;
        firsttime=keyvalueViewModel.get("firsttime");
        if(firsttime==null){
            keyvalueViewModel.insert(new keyvalue("firsttime","yes"));
        }
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
                Mlog.e("Control","Permission: "+permissions[i]+ "was "+grantResults[i]);
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
        toolbar = findViewById(R.id.toolbar);
        toolbar_text=findViewById(R.id.toolbar_title);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        fabx=findViewById(R.id.fabnew);
        fabx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                redbuttonhelper();
            }
        });
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        });
        bottomNavigationView = findViewById(R.id.customBottomBar);
        bottomNavigationView.inflateMenu(R.menu.activity_main_drawer1);
        NavigationUI.setupWithNavController(bottomNavigationView,
                navController);
        locationhelper();
        Airhelper();
        MitiService mitiService=new MitiService(1);
        mitiService.schedule(new FeedClean(),80,360,TimeUnit.SECONDS);
        mitiService.schedule(new UpdateChatlist(),0,120, TimeUnit.SECONDS);
        mitiService.schedule(new ChatSync(),5,60, TimeUnit.SECONDS);
        mitiService.schedule(new DownloadChatImage(),10,60, TimeUnit.SECONDS);
        mitiService.schedule(new SecuritySync(),15,60, TimeUnit.SECONDS);
        mitiService.schedule(new DiarySync(),0,120, TimeUnit.SECONDS);
        mitiService.schedule(new MessageRequestSync(),5,120,TimeUnit.SECONDS);
        mitiService.schedule(new GetMessageRequestSync(),10,120,TimeUnit.SECONDS);

        if(!isMyServiceRunning(SendLoc.class)){
            Mlog.e("Service started");
            startService(new Intent(getApplicationContext(), SendLoc.class));
        }else{
            Mlog.e("Service already running");
            stopService(new Intent(getApplicationContext(), SendLoc.class));
        }
        new POSTRequest().execute("updateUserLocation",new Gson().toJson(new Mitigps(Latitude,Longitude)),MainActivity.MeetiCookie);
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
        Mlog.e("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Mlog.e("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Mlog.e("Latitude","status");
    }
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
    public static void Airhelper(){
        new AirLocation((Activity)MainActivityContext, true, true, new AirLocation.Callbacks() {
            @Override
            public void onSuccess(@NotNull Location location) {
                    Latitude=new Double(location.getLatitude()).toString();
                    Longitude=new Double(location.getLongitude()).toString();
                    Mitigps gps=new Mitigps(Latitude,Longitude);
                    keyvalue kv=new keyvalue("gps",new Gson().toJson(gps));
                    keyvalueViewModel.insert(kv);
                Mlog.e("mumalocationSuccess",location.toString());
            }

            @Override
            public void onFailed(@NotNull AirLocation.LocationFailedEnum locationFailedEnum) {
                // do something
                Mlog.e("mumalocationfailed,calling again");
                MainActivity.Airhelper();
            }
        });
    }
    public static void killswitch(){
//        ToastHelper.ToastFun(MainActivity.MainActivityContext,"Please close fake location");
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                android.os.Process.killProcess(android.os.Process.myPid());
//            }
//        }, 5000);
    }
    public void locationhelper(){
        keyvalue gpsx=keyvalueViewModel.get("gps");
        if(gpsx!=null){
            Mitigps gpst=new Gson().fromJson(gpsx.mitivalue,Mitigps.class);
            Latitude=gpst.Latitude;
            Longitude=gpst.Longitude;
            Mlog.e("MainActiviy",gpsx.mitivalue);
        }else{
            locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            try{
                Location loc =  locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if(loc!=null){
                    if(mitihelper.isMockLocationOn(loc,MainActivity.MainActivityContext)){
                        killswitch();
                    }else{
                        Latitude=new Double(loc.getLatitude()).toString();
                        Longitude=new Double(loc.getLongitude()).toString();
                    }
                }else{
                    loc =  locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    if(loc==null){
                        return;
                    }
                    if(mitihelper.isMockLocationOn(loc,MainActivity.MainActivityContext)){
                        killswitch();
                    }else{
                        Latitude=new Double(loc.getLatitude()).toString();
                        Longitude=new Double(loc.getLongitude()).toString();
                    }
                }
            }catch (SecurityException e){

            }
        }
    }
    public static void redbuttonhelper(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                no_of_clicks=0;
            }
        }, 3000);
        no_of_clicks=no_of_clicks+1;
        if(no_of_clicks==4){
            AlertPOST.helper1();
            ToastHelper.ToastFun(MainActivityContext,"LocationSent");
        }else{
            ToastHelper.ToastFun(MainActivityContext,Integer.toString(4-no_of_clicks)+" more");
        }
        Airhelper();
    }
    public static void redbuttonhelper1(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                no_of_clicks=0;
            }
        }, 3000);
        no_of_clicks=no_of_clicks+1;
        if(no_of_clicks==4){
            AlertPOST.helper1();
            ToastHelper.ToastFun(MainActivityContext,"LocationSent");
        }else{
        }
        Airhelper();
    }
}