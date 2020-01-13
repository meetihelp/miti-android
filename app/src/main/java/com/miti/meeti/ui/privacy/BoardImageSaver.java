package com.miti.meeti.ui.privacy;

import com.miti.meeti.MainActivity;
import com.miti.meeti.database.Diary.Moodboard;
import com.miti.meeti.mitiutil.try123;
import com.miti.meeti.mitiutil.uihelper.ImageSaver;

import java.io.File;

public class BoardImageSaver extends ImageSaver {
    @Override
    protected void onPostExecute(String s) {
        MainActivity.moodboardViewModel.insert(new Moodboard(try123.randomAlphaNumeric(32),
                try123.mitidt(),"image",null,s));
    }
}
