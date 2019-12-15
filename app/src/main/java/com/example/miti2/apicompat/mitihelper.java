package com.example.miti2.apicompat;

import android.os.Build;
import android.view.View;

import androidx.core.view.ViewCompat;

public class mitihelper {
    public static int getuniqueid(){
        int id;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            id= View.generateViewId();

        } else{
            id= ViewCompat.generateViewId();
        }
        return id;
    }
}
