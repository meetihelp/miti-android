package com.example.miti2.mitiutil.uihelper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
    public static void ToastFun(Context c,String data){
        Toast.makeText(c,data,Toast.LENGTH_SHORT);
    }
}
