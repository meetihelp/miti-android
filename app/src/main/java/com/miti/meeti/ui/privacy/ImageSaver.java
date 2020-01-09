package com.miti.meeti.ui.privacy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.mitiutil.Logging.Mlog;
import com.miti.meeti.mitiutil.try123;

import java.io.File;
import java.io.FileOutputStream;

public class ImageSaver extends AsyncTask<String,Void,Void> {
    public ImageSaver() {
        super();
    }

    @Override
    protected Void doInBackground(String... strings) {
        String n= try123.randomAlphaNumeric(32);
        String fname = "Moodboard-" + n + ".jpg";
        String path=MainActivity.RootFolder+File.separator+"Moodboards";
        try123.createifnot(path);
        File file = new File(path, fname);
        Bitmap bMap = BitmapFactory.decodeFile(strings[0]);
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
        MainActivity.moodboardViewModel.insert(new Moodboard(try123.randomAlphaNumeric(32),
                try123.mitidt(),"image",null,path+File.separator+fname));
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
