package com.example.miti2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.miti2.mitiListener.MitiEventListener;

public class MainActivityStartup extends AppCompatActivity implements MitiEventListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_startup);
    }

    @Override
    public void sendDataToActivity(String Data){
        Log.e("Compat",Data);
    }
}
