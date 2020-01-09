package com.miti.meeti.mitiutil.uihelper;

import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.miti.meeti.mitiutil.Logging.Mlog;

import java.util.ArrayList;
import java.util.List;

public class PermissionHelper {
    public  static boolean PermissionGranted(String[] which, AppCompatActivity that) {
        List<String>temp=new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for(int i=0;i<which.length;i++){
                if (that.checkSelfPermission(which[i])
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
        ActivityCompat.requestPermissions(that, temp.toArray(new String[temp.size()]), 1);
        Mlog.e("Main bhi call ho gaya");
        return false;
    }
}
