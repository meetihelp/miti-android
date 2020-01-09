package com.miti.meeti;

import android.Manifest;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.miti.meeti.bottomnav.CurvedBottomNavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.uihelper.PermissionHelper;

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
    private static AppBarLayout appBarLayout;
    public static String RootFolder;
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
        PermissionGranted(new String[] {android.Manifest.permission.READ_EXTERNAL_STORAGE
                , Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA});
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
    }
    public  boolean PermissionGranted(String[] which) {
        List<String> temp=new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for(int i=0;i<which.length;i++){
                if (checkSelfPermission(which[i])
                        == PackageManager.PERMISSION_GRANTED) {
                    Mlog.e("Permission is granted->"+which[i]);
                } else {
                    Mlog.e("Permission is not granted->"+which[i]);
                    temp.add(which[i]);
                }
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Mlog.e("Control","Permission is granted");
            return true;
        }
        if(temp.size()==0){
            return true;
        }
        ActivityCompat.requestPermissions(this,temp.toArray(new String[temp.size()]), 1);
        Mlog.e("Main bhi call ho gaya");
        return false;
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