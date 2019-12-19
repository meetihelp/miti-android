package com.miti.meeti;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.miti.meeti.mitiListener.MitiEventListener;

public class MainActivityStartup extends AppCompatActivity implements MitiEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_startup);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        getSupportActionBar().hide();
//        trans.startTransition(6000);
    }

    @Override
    public void sendDataToActivity(String Data){
        Log.e("Compat",Data);
    }
}
