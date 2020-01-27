package com.miti.meeti.apicompat;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.provider.Settings;
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
    public static boolean isMockLocationOn(Location location, Context context) {
        return false;
//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
//            return location.isFromMockProvider();
//        } else {
//            String mockLocation = "0";
//            try {
//                mockLocation = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOW_MOCK_LOCATION);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return !mockLocation.equals("0");
//        }
    }
}
