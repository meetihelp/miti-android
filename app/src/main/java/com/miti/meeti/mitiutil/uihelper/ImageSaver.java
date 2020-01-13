package com.miti.meeti.mitiutil.uihelper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Chat.ChatDb;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.try123;

import java.io.File;
import java.io.FileOutputStream;

public class ImageSaver extends AsyncTask<String,Void,String> {
    public ImageSaver() {
        super();
    }

    @Override
    protected String doInBackground(String... strings) {
        //pass two string string0 means where to save, string 1 means what prefix to use, strings2
        String n= try123.randomAlphaNumeric(32);
        String fname = strings[1] + n + ".jpg";
        String path=MainActivity.RootFolder+File.separator+strings[0];
        try123.createifnot(path);
        File file = new File(path, fname);
        Bitmap bMap = BitmapFactory.decodeFile(strings[2]);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bMap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            Mlog.e(e);
        }
        return path+"/"+fname;
//        if(strings[1].contains("Moodboard")){
//            MainActivity.moodboardViewModel.insert(new Moodboard(try123.randomAlphaNumeric(32),
//                    try123.mitidt(),"image",null,path+File.separator+fname));
//        }else if(strings[1].contains("Chat")){
//
//            MainActivity.chatDbViewModel.insert(new ChatDb());
//        }
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
