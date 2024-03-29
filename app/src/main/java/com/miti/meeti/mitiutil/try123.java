package com.miti.meeti.mitiutil;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.miti.meeti.MainActivity;
import com.miti.meeti.R;
import com.miti.meeti.mitiutil.Logging.Mlog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class try123 {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static String randomAlphaNumeric(int count) {
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }
    public static String pathhelper(String subdir,String prefix){
        String n= try123.randomAlphaNumeric(32);
        String fname = prefix + n + ".jpg";
        String path= MainActivity.RootFolder+ File.separator+subdir;
        try123.createifnot(path);
        return path+File.separator+fname;
    }
    public static String mitidt(){
        String mitidt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());
        return mitidt;
    }
    public static Date getdate(String dt){
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try{
            return format1.parse(dt);
        }catch (Exception e){
            Mlog.e("try123.java->","getdate(String dt)",e.toString());
            return null;
        }
    }
    public static Void createifnot(String path){
        File folder = new File(path);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }else{
            Mlog.e("",path,"folder already present");
        }
        if (success) {
            Mlog.e("folder created");
        } else {
            Mlog.e("folder creation failed");
        }
        return null;
    }
    public static String getname(String chatid){
        return "AdamEve-"+chatid.substring(0,3);
    }
    public static Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null)
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        else
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(ContextCompat.getColor(MainActivity.MainActivityContext, R.color.mitiPrimaryBlue));
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }
    public static boolean check_email(String email){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+"[a-zA-Z0-9_+&*-]+)*@" +"(?:[a-zA-Z0-9-]+\\.)+[a-z" +"A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
